package ro.teamnet.ou.service;

import ro.teamnet.ou.security.UserOrganizationalUnitDetails;

/**
 * Created by Oana.Mihai on 10/9/2015.
 */
public interface OrganizationalUnitLoginService {
    UserOrganizationalUnitDetails getUserOrganizationalUnitDetails();

    UserOrganizationalUnitDetails authenticateInAllOrganizations();

    UserOrganizationalUnitDetails authenticateInOrganization(Long organizationId);

    UserOrganizationalUnitDetails authenticateInOU(Long organizationId, Long ouId);

    void updateUserFunctions(String username, UserOrganizationalUnitDetails userOuDetails);
}
