package ro.teamnet.ou.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.ou.service.OrganizationService;
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
    OrganizationalUnitService ouService;

    @Inject
    OrganizationService orgService;

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
}
