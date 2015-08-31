package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mapper.AccountMapper;
import ro.teamnet.ou.mapper.FunctionMapper;
import ro.teamnet.ou.repository.jpa.AccountFunctionRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitFunctionRepository;
import ro.teamnet.ou.repository.neo.AccountNeoRepository;
import ro.teamnet.ou.repository.neo.FunctionNeoRepository;
import ro.teamnet.ou.web.rest.dto.AccountDTO;

import javax.inject.Inject;
import java.util.*;

@Service
public class OUAccountServiceImpl implements OUAccountService {
    @Inject
    private AccountNeoRepository accountNeoRepository;
    @Inject
    private FunctionNeoRepository functionNeoRepository;
    @Inject
    private AccountFunctionRepository accountFunctionRepository;
    @Inject
    private OrganizationalUnitFunctionRepository ouFunctionRepository;

    @Override
    public List<Long> getOrganizationalUnitIds(Long accountId) {
        ro.teamnet.ou.domain.neo.Account neoAccount = accountNeoRepository.findByJpaId(accountId);
        List<Long> ouIds = new ArrayList<>();
        if (neoAccount != null && neoAccount.getOrganizationalUnits() != null) {
            for (OrganizationalUnit organizationalUnit : neoAccount.getOrganizationalUnits()) {
                ouIds.add(organizationalUnit.getJpaId());
            }
        }
        return ouIds;
    }

    @Override
    public Collection<AccountDTO> getAccountsInOrganizationalUnit(Long organizationalUnitId) {
        Map<Long, AccountDTO> accountsById = new HashMap<>();
        Set<ro.teamnet.ou.domain.neo.Function> functions = functionNeoRepository.findByOrganizationalUnitJpaId(
                organizationalUnitId);
        if (functions != null && !functions.isEmpty()) {
            for (ro.teamnet.ou.domain.neo.Function function : functions) {
                AccountDTO accountDTO = accountsById.get(function.getAccount().getId());
                if (accountDTO == null) {
                    accountDTO = AccountMapper.toDTO(function.getAccount());
                    accountsById.put(accountDTO.getId(), accountDTO);
                }
                accountDTO.getFunctions().add(FunctionMapper.toDTO(function));
            }
        }
        return accountsById.values();
    }

    @Override
    public Collection<AccountDTO> getAccountsEligibleForOrganizationalUnit(Long organizationalUnitId) {
        Map<Long, AccountDTO> accountsById = new HashMap<>();
        Set<Function> functions = ouFunctionRepository.findFunctionsByOrganizationalUnitId(organizationalUnitId);
        if (functions != null && !functions.isEmpty()) {
            Set<AccountFunction> accountFunctions = accountFunctionRepository.findByFunctionIn(functions);
            for (AccountFunction accountFunction : accountFunctions) {
                AccountDTO accountDTO = accountsById.get(accountFunction.getAccount().getId());
                if (accountDTO == null) {
                    accountDTO = AccountMapper.toDTO(accountFunction.getAccount());
                    accountsById.put(accountDTO.getId(), accountDTO);
                }
                accountDTO.getFunctions().add(FunctionMapper.toDTO(accountFunction.getFunction(), true));
            }
        }
        return accountsById.values();
    }
}
