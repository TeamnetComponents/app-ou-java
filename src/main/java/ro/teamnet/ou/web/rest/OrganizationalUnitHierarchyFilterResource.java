package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.ou.acl.domain.OrganizationalUnitUserDetails;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oana.Mihai on 9/2/2015.
 */
@RestController
@RequestMapping("/app/rest/organizationalUnitHierarchyFilter")
public class OrganizationalUnitHierarchyFilterResource {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OrganizationalUnitDTO> getOrganizationalUnitsForCurrentUser(){
        User authenticatedUser = SecurityUtils.getAuthenticatedUser();
        if (authenticatedUser instanceof OrganizationalUnitUserDetails) {
            return ((OrganizationalUnitUserDetails) authenticatedUser).getOrganizationalUnits();
        }
        return new ArrayList<>();
    }
}
