package ro.teamnet.ou.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.ou.security.UserOrganizationalUnitDetails;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.service.OrganizationalUnitLoginService;
import ro.teamnet.ou.service.OrganizationalUnitService;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by Oana.Mihai on 10/5/2015.
 */
@RestController
public class OrganizationalUnitLoginResource {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private OrganizationalUnitService ouService;

    @Inject
    private OrganizationService orgService;

    @Inject
    private OrganizationalUnitLoginService ouLoginService;

    @RequestMapping(value = "/app/public/rest/ou/{organizationId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OrganizationalUnitDTO> getPublicOUsByOrganizationId(@PathVariable Long organizationId) {
        log.debug("REST request to GET all public OUs within the organization with id: {}", organizationId);
        return ouService.getPublicOUsByOrganizationId(organizationId);
    }

    @RequestMapping(value = "/app/public/rest/organization", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OrganizationDTO> getPublicOrganizations() {
        log.debug("REST request to GET all public organizations");
        return orgService.getPublicOrganizations();
    }

    @RequestMapping(value = "/app/rest/loginStep/organizations", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OrganizationDTO> getOrganizationsForCurrentUser() {
        log.debug("REST request to GET the organizations of the current user");
        return orgService.getOrganizationsForCurrentUser();
    }

    @RequestMapping(value = "/app/rest/loginStep/organizationalUnits/{organizationId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OrganizationalUnitDTO> getOUsForCurrentUser(@PathVariable Long organizationId) {
        log.debug("REST request to GET the OUs of the current user and selected organization");
        return ouService.getOUsForCurrentUser(organizationId);
    }

    @RequestMapping(value = "/app/rest/loginStep/authenticate", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticateInAllOrganizations() {
        log.debug("REST request to authenticate user in all organizations");
        ouLoginService.authenticateInAllOrganizations();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/app/rest/loginStep/authenticate/{organizationId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticateInOrganization(@PathVariable Long organizationId) {
        log.debug("REST request to authenticate user in organization with id: {}", organizationId);
        ouLoginService.authenticateInOrganization(organizationId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/app/rest/loginStep/authenticate/{organizationId}/{ouId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticateInOU(@PathVariable Long organizationId, @PathVariable Long ouId) {
        log.debug("REST request to authenticate user in organization with id {} and OU with id {}", organizationId, ouId);
        ouLoginService.authenticateInOU(organizationId, ouId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/app/rest/loginStep/authenticationInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserOrganizationalUnitDetails getUserOrganizationalUnitDetails() {
        log.debug("REST request to get OU authenticate info");
        return ouLoginService.getUserOrganizationalUnitDetails();
    }
}
