package ro.teamnet.ou.service;

import java.util.List;

/**
 * Service for handling accounts associated to organizational units.
 */
public interface OUAccountService {
    List<Long> getOrganizationalUnitIds(Long accountId);
}
