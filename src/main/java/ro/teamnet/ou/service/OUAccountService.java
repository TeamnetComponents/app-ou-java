package ro.teamnet.ou.service;

import ro.teamnet.ou.web.rest.dto.AccountDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.Collection;
import java.util.List;

/**
 * Service for handling accounts associated to organizational units.
 */
public interface OUAccountService {
    List<OrganizationalUnitDTO> getOrganizationalUnits(Long accountId);

    Collection<AccountDTO> getAccountsInOrganizationalUnit(Long organizationalUnitId);

    Collection<AccountDTO> getAccountsEligibleForOrganizationalUnit(Long organizationalUnitId);

    void createOrUpdateOUAccountRelationships(Long ouId, Collection<AccountDTO> accounts);

    void deleteOuAccountRelationships(Long ouId, Long accountId);
}
