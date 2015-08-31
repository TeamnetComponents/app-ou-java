package ro.teamnet.ou.service;

import ro.teamnet.ou.web.rest.dto.AccountDTO;

import java.util.Collection;
import java.util.List;

/**
 * Service for handling accounts associated to organizational units.
 */
public interface OUAccountService {
    List<Long> getOrganizationalUnitIds(Long accountId);

    Collection<AccountDTO> getAccountsInOrganizationalUnit(Long organizationalUnitId);

    Collection<AccountDTO> getAccountsEligibleForOrganizationalUnit(Long organizationalUnitId);
}
