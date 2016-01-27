package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.repository.AccountRepository;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.jpa.OrganizationAccount;
import ro.teamnet.ou.mapper.AccountMapper;
import ro.teamnet.ou.repository.jpa.OrganizationAccountRepository;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;
import ro.teamnet.ou.web.rest.dto.AccountDTO;
import ro.teamnet.ou.web.rest.dto.AccountOrganizationsDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marian.Spoiala on 10/12/2015.
 */
@Service
public class OrganizationAccountServiceImpl implements OrganizationAccountService {

    @Inject
    private AccountService accountService;

    @Inject
    private OrganizationAccountRepository organizationAccountRepository;

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private AccountRepository accountRepository;

    @Override
    public Set<AccountDTO> getAvailableAccounts() {
        List<Account> accounts = accountService.findAll();

        Set<AccountDTO> accountDTOs = new HashSet<>();
        for (Account account : accounts) {
            accountDTOs.add(AccountMapper.toDTO(account));
        }

        return accountDTOs;
    }

    public Set<AccountDTO> getAccountsByOrgId(Long orgId) {
        Set<Account> accounts = organizationAccountRepository.getAccountsByOrgId(orgId);

        Set<AccountDTO> accountDTOs = new HashSet<>();
        for (Account account : accounts) {
            accountDTOs.add(AccountMapper.toDTO(account));
        }

        return accountDTOs;
    }

    /**
     *  This returns all application accounts regardless if they have
     *  organizations assigned or not
     */
    public List<AccountOrganizationsDTO> getAllAccountsWithOrganizations() {
        List<AccountOrganizationsDTO> allAccountsWithOrganizations = new ArrayList<>();
        List<ro.teamnet.bootstrap.web.rest.dto.AccountDTO> bootstrapAccountsDTOs = accountService.findAllExtended();

        for (ro.teamnet.bootstrap.web.rest.dto.AccountDTO bootstrapAccountDTO : bootstrapAccountsDTOs) {
            AccountOrganizationsDTO accountWithOrganizationsDTO = new AccountOrganizationsDTO();
            accountWithOrganizationsDTO.setAccount(bootstrapAccountDTO);
            accountWithOrganizationsDTO.setOrganizations(getOrgsByAccountId(bootstrapAccountDTO.getId()));
            allAccountsWithOrganizations.add(accountWithOrganizationsDTO);
        }

        return allAccountsWithOrganizations;
    }

    public Boolean itContains(Set<AccountDTO> accountDTOs, Long id) {
        for (AccountDTO accountDTO : accountDTOs) {
            if (accountDTO.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Boolean isInOrgAccSet(Set<OrganizationAccount> organizationAccounts, Long accountId) {
        for (OrganizationAccount organizationAccount : organizationAccounts) {
            if (organizationAccount.getAccount().getId().equals(accountId))
                return true;
        }
        return false;
    }

    @Override
    public void createOrUpdateOrgAccounts(Long orgId, Set<AccountDTO> accountDTOs) {
        Organization organization = organizationRepository.findOne(orgId);
        Set<OrganizationAccount> organizationAccounts = organizationAccountRepository.findByOrganizationId(orgId);

        Set<OrganizationAccount> finalSaveSet = new HashSet<>();
        Set<OrganizationAccount> finalDeleteSet = new HashSet<>();

        for (OrganizationAccount organizationAccount : organizationAccounts) {
            if (itContains(accountDTOs, organizationAccount.getAccount().getId())) {
                finalSaveSet.add(organizationAccount);
            } else {
                finalDeleteSet.add(organizationAccount);
            }
        }

        OrganizationAccount organizationAccount;
        for (AccountDTO accountDTO : accountDTOs) {
            if (!isInOrgAccSet(finalSaveSet, accountDTO.getId())) {
                organizationAccount = new OrganizationAccount();
                organizationAccount.setAccount(accountRepository.findOne(accountDTO.getId()));
                organizationAccount.setOrganization(organization);
                finalSaveSet.add(organizationAccount);
            }
        }

        organizationAccountRepository.save(finalSaveSet);
        organizationAccountRepository.delete(finalDeleteSet);
    }

    @Override
    public Set<Organization> getOrgsByAccountId(Long accountId) {
        return organizationAccountRepository.getOrgsByAccountId(accountId);
    }

    @Override
    public Set<Organization> getOrgsByAccountUsername(String accountUsername) {
        return organizationAccountRepository.getOrgsByAccountUsername(accountUsername);

    }
}
