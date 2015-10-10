package ro.teamnet.ou.security;

import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.Collection;
import java.util.HashSet;

/**
 * A POJO holder for information about the organizational units associated with the authenticated user.
 */
public class UserOrganizationalUnitDetails {
    private OrganizationDTO authorizedOrganization;
    private Collection<OrganizationalUnitDTO> authorizedOUs;
    private Collection<Long> organizationalUnitIds;
    private Collection<Function> functions;

    public UserOrganizationalUnitDetails(OrganizationDTO authorizedOrganization,
                                         Collection<OrganizationalUnitDTO> authorizedOUs) {
        setAuthorizedOrganization(authorizedOrganization);
        setAuthorizedOUs(authorizedOUs);
    }
    public OrganizationDTO getAuthorizedOrganization() {
        return authorizedOrganization;
    }

    public void setAuthorizedOrganization(OrganizationDTO authorizedOrganization) {
        this.authorizedOrganization = authorizedOrganization;
    }

    public Collection<OrganizationalUnitDTO> getAuthorizedOUs() {
        return authorizedOUs;
    }

    public void setAuthorizedOUs(Collection<OrganizationalUnitDTO> authorizedOUs) {
        this.authorizedOUs = authorizedOUs;
        this.organizationalUnitIds = getAuthorizedOUIds();
    }
    private Collection<Long> getAuthorizedOUIds() {
        Collection<Long> authorizedOuIds = new HashSet<>();
        for (OrganizationalUnitDTO authorizedOU : authorizedOUs) {
            authorizedOuIds.add(authorizedOU.getId());
        }
        return authorizedOuIds;
    }

    public Collection<Long> getOrganizationalUnitIds() {
        return organizationalUnitIds;
    }

    public Collection<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Collection<Function> functions) {
        this.functions = functions;
    }
}
