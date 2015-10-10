package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by Radu.Hoaghe on 8/3/2015.
 */
@RestController
@RequestMapping("/app/rest/organization")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    @Inject
    private OrganizationService organizationService;

    @RequestMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<OrganizationDTO> updateOrganization(@RequestBody OrganizationDTO organizationDTO) {
        if(organizationDTO.getId()!=null)    {
            organizationDTO = organizationService.update(organizationDTO);
        }else {
            organizationDTO = organizationService.save(organizationDTO);
        }
        if (organizationDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(organizationDTO, HttpStatus.OK);
    }


//    @RequestMapping(value = "/deleteOrganization",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            method = RequestMethod.POST)
//    public void deleteOrganization(@RequestBody OrganizationDTO organizationDTO) {
//
//        organizationService.delete(organizationDTO);
//    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    @Timed
    public void delete(@PathVariable("id") Long id) {
        log.debug("REST request to delete : {}", id);
        organizationService.delete(id);
    }

    @RequestMapping(value = "/getOrganizationById/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationDTO> getById(@PathVariable Long id) {

        log.debug("REST request to get Organization by id : {}", id);
        return new ResponseEntity<OrganizationDTO>(organizationService.findOrganizationDTOById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllOrganizations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<OrganizationDTO>> getAll() {

        log.debug("REST request to get all: OrganizationDTOs");
        return new ResponseEntity<Set<OrganizationDTO>>(organizationService.getAllOrganizationDTOs(), HttpStatus.OK);
    }
}
