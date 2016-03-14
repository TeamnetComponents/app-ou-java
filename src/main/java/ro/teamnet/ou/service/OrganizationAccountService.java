package ro.teamnet.ou.service;

import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.web.rest.dto.AccountDTO;

import java.util.Set;

/**
 * Created by Marian.Spoiala on 10/12/2015.
 */
public interface OrganizationAccountService {

    Set<AccountDTO> getAvailableAccounts();

    Set<AccountDTO> getAccountsByOrgId(Long orgId);

    void createOrUpdateOrgAccounts(Long orgId, Set<AccountDTO> accountDTOs);

    Set<Organization> getOrgsByAccountId(Long accountId);

    Set<Organization> getOrgsByAccountUsername(String accountUsername);
}
