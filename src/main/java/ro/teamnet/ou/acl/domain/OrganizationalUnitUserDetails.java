package ro.teamnet.ou.acl.domain;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ro.teamnet.ou.acl.service.OrganizationalUnitUserDetailsPlugin;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.List;

/**
 * An {@link UserDetails} implementation that allows saving information about the organizational units associated with
 * the user.
 * @see OrganizationalUnitUserDetailsPlugin
 */
public class OrganizationalUnitUserDetails extends User implements UserDetails {

    private final List<Long> organizationalUnitIds;
    private final List<OrganizationalUnitDTO> organizationalUnits;

    public OrganizationalUnitUserDetails(UserDetails userDetails, List<Long> organizationalUnitId, List<OrganizationalUnitDTO> organizationalUnits) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        this.organizationalUnitIds = organizationalUnitId;
        this.organizationalUnits = organizationalUnits;
    }

    public List<Long> getOrganizationalUnitIds() {
        return organizationalUnitIds;
    }

    public List<OrganizationalUnitDTO> getOrganizationalUnits() {
        return organizationalUnits;
    }
}
