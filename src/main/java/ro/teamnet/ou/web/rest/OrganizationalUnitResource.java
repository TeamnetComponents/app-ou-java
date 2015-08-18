package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.service.OrganizationalUnitService;
import ro.teamnet.ou.service.PerspectiveService;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Radu.Hoaghe on 8/3/2015.
 */
@RestController
@RequestMapping("/app/rest/organizationalUnit")
public class OrganizationalUnitResource {
    //extends ro.teamnet.bootstrap.web.rest.AbstractResource<OrganizationalUnit, Long> {

    private final Logger log = LoggerFactory.getLogger(OrganizationalUnitResource.class);
    @Inject
    private OrganizationalUnitService organizationalUnitService;
    @Inject
    private OrganizationService organizationService;
    @Inject
    private PerspectiveService perspectiveService;

//    @Inject
//    public OrganizationalUnitResource(OrganizationalUnitService organizationalUnitService) {
//        super(organizationalUnitService);
//        this.organizationalUnitService = organizationalUnitService;
//    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Set<OrganizationalUnitDTO>> getAllOu() {
        log.debug("REST request to get all: {}");
        Set<OrganizationalUnitDTO> organizationalUnitDTOs = organizationalUnitService.getAllOrganizationalUnit();

        return new ResponseEntity<Set<OrganizationalUnitDTO>>(organizationalUnitDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationalUnitDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);

        OrganizationalUnitDTO organizationalUnitDTO = organizationalUnitService.findOrganizationalUnitById(id);

        return new ResponseEntity<OrganizationalUnitDTO>(organizationalUnitDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationalUnitDTO> update(@PathVariable Long id, @RequestBody OrganizationalUnitDTO organizationalUnitDTO) {
        log.debug("REST request to update the function : {}", id);
        OrganizationalUnitDTO organizationalUnitDTOResp = organizationalUnitService.save(organizationalUnitDTO);

        return new ResponseEntity<>(organizationalUnitDTOResp, HttpStatus.OK);
    }
}
