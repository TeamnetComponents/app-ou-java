package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.service.OrganizationalUnitService;
import ro.teamnet.ou.service.PerspectiveService;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Radu.Hoaghe on 8/3/2015.
 */
@RestController
@RequestMapping("/app/rest/organization")
public class OrganizationResource {
    //extends ro.teamnet.bootstrap.web.rest.AbstractResource<Organization,Long>{

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);
    @Inject
    private OrganizationService organizationService;
    @Inject
    private OrganizationalUnitService organizationalUnitService;
    @Inject
    private PerspectiveService perspectiveService;

//    @Inject
//    public OrganizationResource(OrganizationService organizationService) {
//        super(organizationService);
//        this.organizationService = organizationService;
//    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);

        Organization organization = organizationService.findOrganizationById(id);
        ro.teamnet.ou.domain.neo.Organization organizationNeo = organizationService.findOrganizationNeoById(id);
        OrganizationDTO organizationDTO = organizationService.toOrganizationDTO(organization, organizationNeo);

        if (organization == null) {
            return new ResponseEntity<OrganizationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<OrganizationDTO>(organizationDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationDTO> update(@PathVariable Long id, @RequestBody OrganizationDTO organizationDTO) {
        log.debug("REST request to update the function : {}", id);

        Organization organization = organizationService.findOrganizationById(id);
        ro.teamnet.ou.domain.neo.Organization organizationNeo = organizationService.findOrganizationNeoById(id);
        if (organization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OrganizationDTO dto = organizationService.update(organization, organizationNeo, organizationDTO);
//        organizationService.updateOrganization(organization, organizationDTO);
//        organizationService.updateOrganizationNeo(organizationNeo, organizationDTO);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<OrganizationDTO>> getAll() {
        log.debug("REST request to get all: {}");

        List<Organization> organizationList = organizationService.getAllOrganization();
        List<ro.teamnet.ou.domain.neo.Organization> organizationListNeo = organizationService.gelAllOrganizationNeo();
        Set<OrganizationDTO> organizationDTOs = new HashSet<>();
        if(organizationList != null && organizationListNeo != null) {
            for (int i = 0; i < organizationList.size(); i++) {
                organizationDTOs.add(organizationService.toOrganizationDTO(organizationList.get(i), organizationListNeo.get(i)));
            }
        }

        return new ResponseEntity<Set<OrganizationDTO>>(organizationDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/createOrganization",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @Timed
    public ResponseEntity<OrganizationDTO> saveAccount(@RequestBody OrganizationDTO organizationDTO) {

        organizationDTO = organizationService.create(organizationDTO);
        if (organizationDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizationDTO, HttpStatus.OK);
    }

    private Set<OrganizationDTO> checkConnectionWithFrontend() {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss.Z");
        try {
            date = dateFormat.parse("10/10/2015");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        OrganizationalUnitDTO organizationalUnitChild = organizationalUnitService.createOrganizationalUnitDTO(2l,
                "Organizational Unit 2", "Org Unit", date, date, null, null, null);

        OrganizationalUnitDTO organizationalUnitChild1 = organizationalUnitService.createOrganizationalUnitDTO(3l,
                "Organizational Unit 3", "Org Unit", date, date, null, null, null);

        Set<OrganizationalUnitDTO> organizationalUnitDTOset = new HashSet<>();
        organizationalUnitDTOset.add(organizationalUnitChild);
        organizationalUnitDTOset.add(organizationalUnitChild1);

        OrganizationalUnitDTO organizationalUnit = organizationalUnitService.createOrganizationalUnitDTO(4l,
                "Organizational Unit 4", "Org Unit", date, date, null, null, organizationalUnitDTOset);

        OrganizationalUnitDTO organizationalUnit5 = organizationalUnitService.createOrganizationalUnitDTO(5l,
                "Organizational Unit 5", "Org Unit", date, date, null, null, null);

        OrganizationalUnitDTO organizationalUnit6 = organizationalUnitService.createOrganizationalUnitDTO(6l,
                "Organizational Unit 6", "Org Unit", date, date, null, null, null);

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        organizationalUnitDTOs.add(organizationalUnit5);
        organizationalUnitDTOs.add(organizationalUnit6);
        organizationalUnitDTOs.add(organizationalUnit);

        PerspectiveDTO perspectiveDTO = perspectiveService.createPerspectiveDTO(1l, "Perspective 1", "Persp description for 1st", null, organizationalUnitDTOs);
        PerspectiveDTO perspectiveDTO1 = perspectiveService.createPerspectiveDTO(2l, "Perspective 2", "Persp description for 2nd", null, organizationalUnitDTOs);
        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        perspectiveDTOs.add(perspectiveDTO);
        perspectiveDTOs.add(perspectiveDTO1);

        OrganizationDTO organizationDTO = organizationService.createOrganizationDTO(1l, "Org 1", "Org description for 1st", date, date, perspectiveDTOs);
        OrganizationDTO organizationDTO1 = organizationService.createOrganizationDTO(2l, "Org 2", "Org description for 2nd", date, date, perspectiveDTOs);

        Set<OrganizationDTO> organizationDTOs = new HashSet<>();
        organizationDTOs.add(organizationDTO);
        organizationDTOs.add(organizationDTO1);

        return organizationDTOs;
    }
}
