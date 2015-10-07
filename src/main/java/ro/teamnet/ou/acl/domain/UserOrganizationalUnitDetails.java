package ro.teamnet.ou.acl.domain;

import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.Collection;
import java.util.HashSet;

/**
 * A POJO holder for information about the organizational units associated with the authenticated user.
 */
public class UserOrganizationalUnitDetails {
    private final Collection<Long> organizationalUnitIds;
    private final Collection<OrganizationalUnitDTO> organizationalUnits;
    private OrganizationDTO authorizedOrganization;

    public UserOrganizationalUnitDetails(OrganizationDTO authorizedOrganization,
                                         Collection<OrganizationalUnitDTO> authorizedOUs) {
        this.authorizedOrganization = authorizedOrganization;
        this.organizationalUnits = authorizedOUs;
        this.organizationalUnitIds = getAuthorizedOUIds();
    }

    private Collection<Long> getAuthorizedOUIds() {
        Collection<Long> authorizedOuIds = new HashSet<>();
        for (OrganizationalUnitDTO authorizedOU : organizationalUnits) {
            authorizedOuIds.add(authorizedOU.getId());
        }
        return authorizedOuIds;
    }

    public Collection<Long> getOrganizationalUnitIds() {
        return organizationalUnitIds;
    }

    public Collection<OrganizationalUnitDTO> getOrganizationalUnits() {
        return organizationalUnits;
    }

    public OrganizationDTO getAuthorizedOrganization() {
        return authorizedOrganization;
    }

    public void setAuthorizedOrganization(OrganizationDTO authorizedOrganization) {
        this.authorizedOrganization = authorizedOrganization;
    }
}
