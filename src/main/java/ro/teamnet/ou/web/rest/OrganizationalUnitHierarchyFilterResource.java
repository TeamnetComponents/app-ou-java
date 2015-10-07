package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.bootstrap.plugin.security.UserDetailsExtension;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.ou.acl.domain.UserOrganizationalUnitDetails;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.Collection;
import java.util.HashSet;

import static ro.teamnet.ou.acl.service.OrganizationalUnitUserDetailsPlugin.USER_ORGANIZATIONAL_UNIT_DETAILS;

/**
 * Created by Oana.Mihai on 9/2/2015.
 */
@RestController
@RequestMapping("/app/rest/organizationalUnitHierarchyFilter")
public class OrganizationalUnitHierarchyFilterResource {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Collection<OrganizationalUnitDTO> getOrganizationalUnitsForCurrentUser(){
        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();
        if (authenticatedUser instanceof UserDetailsExtension) {
            UserOrganizationalUnitDetails userOuDetails = (UserOrganizationalUnitDetails) ((UserDetailsExtension) authenticatedUser).getExtensions().get(USER_ORGANIZATIONAL_UNIT_DETAILS);
            if (userOuDetails != null) {
                return userOuDetails.getOrganizationalUnits();
            }
        }
        return new HashSet<>();
    }
}
