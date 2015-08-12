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

        List<OrganizationalUnit> organizationalUnitList = organizationalUnitService.getAllOrganizationalUnit();
        List<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitListNeo = organizationalUnitService.getAllOrganizationalUnitNeo();
        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        if(organizationalUnitList != null && organizationalUnitListNeo != null) {
            for (int i = 0; i < organizationalUnitList.size(); i++) {
                organizationalUnitDTOs.add(organizationalUnitService.toOrganizationalUnitDTO(organizationalUnitList.get(i), organizationalUnitListNeo.get(i)));
            }
        }

        return new ResponseEntity<Set<OrganizationalUnitDTO>>(organizationalUnitDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationalUnitDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);

        OrganizationalUnit organizationalUnit = organizationalUnitService.findOrganizationalUnitById(id);
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitService.findOrganizationalUnitNeoById(id);
        OrganizationalUnitDTO organizationalUnitDTO = organizationalUnitService.toOrganizationalUnitDTO(organizationalUnit, organizationalUnitNeo);

        if (organizationalUnit == null) {
            return new ResponseEntity<OrganizationalUnitDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<OrganizationalUnitDTO>(organizationalUnitDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationalUnitDTO> update(@PathVariable Long id, @RequestBody OrganizationalUnitDTO organizationalUnitDTO) {
        log.debug("REST request to update the function : {}", id);

        OrganizationalUnit organizationalUnit = organizationalUnitService.findOrganizationalUnitById(id);
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitService.findOrganizationalUnitNeoById(id);

        if (organizationalUnit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        organizationalUnitDTO = organizationalUnitService.update(organizationalUnit, organizationalUnitNeo, organizationalUnitDTO);
//        organizationalUnitService.updateOrganizationalUnit(organizationalUnit, organizationalUnitDTO);
//        organizationalUnitService.updateOrganizationalUnitNeo(organizationalUnitNeo, organizationalUnitDTO);

        return new ResponseEntity<>(organizationalUnitDTO, HttpStatus.OK);
    }
}
