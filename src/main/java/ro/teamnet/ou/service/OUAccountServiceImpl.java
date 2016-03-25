package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.domain.neo.Account;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mapper.AccountMapper;
import ro.teamnet.ou.mapper.FunctionMapper;
import ro.teamnet.ou.mapper.OrganizationalUnitMapper;
import ro.teamnet.ou.repository.jpa.AccountFunctionRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitFunctionRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.neo.*;
import ro.teamnet.ou.web.rest.dto.AccountDTO;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional(value="jpaTransactionManager", readOnly = true)
public class OUAccountServiceImpl implements OUAccountService {

    @Inject
    private AccountNeoRepository accountNeoRepository;
    @Inject
    private OrganizationalUnitNeoRepository ouNeoRepository;
    @Inject
    private FunctionNeoRepository functionNeoRepository;
    @Inject
    private AccountFunctionRepository accountFunctionRepository;
    @Inject
    private OrganizationalUnitFunctionRepository ouFunctionRepository;
    @Inject
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Override
    @Transactional(value = "neo4jTransactionManager",readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<OrganizationalUnitDTO> getOrganizationalUnits(Long accountId) {
        ro.teamnet.ou.domain.neo.Account neoAccount = accountNeoRepository.findByJpaId(accountId);
        List<OrganizationalUnitDTO> organizationalUnitDTOs = new ArrayList<>();
        if (neoAccount != null) {
            Set<OrganizationalUnit> organizationalUnits = neoAccount.getOrganizationalUnits();
            for (OrganizationalUnit organizationalUnit : organizationalUnits) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit, true));
            }
        }
        return organizationalUnitDTOs;
    }

    @Override
    @Transactional(value = "crossStoreTransactionManager",readOnly = true)
    public Collection<AccountDTO> getAccountsInOrganizationalUnit(Long organizationalUnitId) {
        Map<Long, AccountDTO> accountsById = new HashMap<>();
        Set<ro.teamnet.ou.domain.neo.Function> functions = ouNeoRepository.findByJpaId(organizationalUnitId).getFunctions();
        if (functions != null && !functions.isEmpty()) {
            for (ro.teamnet.ou.domain.neo.Function function : functions) {
                AccountDTO accountDTO = accountsById.get(function.getAccount().getJpaId());
                if (accountDTO == null) {
                    accountDTO = AccountMapper.toDTO(function.getAccount());
                    accountsById.put(accountDTO.getId(), accountDTO);
                }
                accountDTO.getFunctions().add(FunctionMapper.toDTO(function));
            }
        }
        Set<Function> availableOuFunctions = ouFunctionRepository.findFunctionsByOrganizationalUnitId(organizationalUnitId);
        for (Long accountId : accountsById.keySet()) {
            Set<Function> availableAccountFunctions = accountFunctionRepository.findFunctionsByAccountId(accountId);
            availableAccountFunctions.retainAll(availableOuFunctions);
            accountsById.get(accountId).setAvailableFunctions(FunctionMapper.toDTO(availableAccountFunctions, true));
        }
        return accountsById.values();
    }

    @Override
    @Transactional(value = "crossStoreTransactionManager",readOnly = true)
    public Collection<AccountDTO> getAccountsEligibleForOrganizationalUnit(Long organizationalUnitId) {
        Map<Long, AccountDTO> accountsById = new HashMap<>();
        Set<Function> availableOuFunctions = ouFunctionRepository.findFunctionsByOrganizationalUnitId(organizationalUnitId);
        if (availableOuFunctions != null && !availableOuFunctions.isEmpty()) {
            Set<AccountFunction> accountFunctions = accountFunctionRepository.findByFunctionIn(availableOuFunctions);
            for (AccountFunction accountFunction : accountFunctions) {
                AccountDTO accountDTO = accountsById.get(accountFunction.getAccount().getId());
                if (accountDTO == null) {
                    accountDTO = AccountMapper.toDTO(accountFunction.getAccount());
                    accountsById.put(accountDTO.getId(), accountDTO);
                }
                accountDTO.getFunctions().add(FunctionMapper.toDTO(accountFunction.getFunction(), true));
                accountDTO.getAvailableFunctions().add(FunctionMapper.toDTO(accountFunction.getFunction(), true));
            }
        }
        return accountsById.values();
    }

    @Override
    @Transactional(value = "crossStoreTransactionManager")
    public void createOrUpdateOUAccountRelationships(Long ouId, Collection<AccountDTO> accounts) {
        OrganizationalUnit neoOrganizationalUnit = ouNeoRepository.findByJpaId(ouId);
        neoOrganizationalUnit.setAccounts(new HashSet<Account>());
        neoOrganizationalUnit.setFunctions(new HashSet<ro.teamnet.ou.domain.neo.Function>());
        ouNeoRepository.save(neoOrganizationalUnit);
        ro.teamnet.ou.domain.jpa.OrganizationalUnit jpaOrganizationalUnit = organizationalUnitRepository
                .getOneWithAccountFunctions(ouId);
        jpaOrganizationalUnit.setAccountFunctions(new HashSet<AccountFunction>());
        for (AccountDTO account : accounts) {
            Account neoAccount = accountNeoRepository.findByJpaId(account.getId());
            for (FunctionDTO functionDTO : account.getFunctions()) {
                saveNeoFunction(neoOrganizationalUnit, neoAccount, functionDTO);
                AccountFunction accountFunction = accountFunctionRepository.findByAccountIdAndFunctionId(account.getId(), functionDTO.getId());
                jpaOrganizationalUnit.getAccountFunctions().add(accountFunction);
            }
        }
        organizationalUnitRepository.save(jpaOrganizationalUnit);
    }

    @Transactional(value="neo4jTransactionManager")
    private void saveNeoFunction(OrganizationalUnit neoOrganizationalUnit, Account neoAccount, FunctionDTO functionDTO) {
        ro.teamnet.ou.domain.neo.Function neoFunction = new ro.teamnet.ou.domain.neo.Function();
        neoFunction.setOrganizationalUnit(neoOrganizationalUnit);
        neoFunction.setAccount(neoAccount);
        neoFunction.setId(null);
        neoFunction.setJpaId(functionDTO.getId());
        neoFunction.setCode(functionDTO.getCode());
        functionNeoRepository.save(neoFunction);
    }

    @Override
    @Transactional(value = "crossStoreTransactionManager")
    public void deleteOuAccountRelationships(Long ouId, Long accountId) {
        functionNeoRepository.deleteByOrganizationalUnitJpaIdAndAccountJpaId(ouId, accountId);
        ro.teamnet.ou.domain.jpa.OrganizationalUnit organizationalUnit = organizationalUnitRepository
                .getOneWithAccountFunctions(ouId);
        organizationalUnit.getAccountFunctions().removeAll(accountFunctionRepository.findByAccountId(accountId));
        organizationalUnitRepository.save(organizationalUnit);
    }
}
