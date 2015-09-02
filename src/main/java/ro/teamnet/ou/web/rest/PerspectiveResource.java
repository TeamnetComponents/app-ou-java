package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.service.PerspectiveService;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
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
@RequestMapping("/app/rest/perspective")
public class PerspectiveResource {

    private final Logger log = LoggerFactory.getLogger(PerspectiveResource.class);
    @Inject
    private PerspectiveService perspectiveService;


    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PerspectiveDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);
        PerspectiveDTO perspectiveDTO = perspectiveService.findPerspectiveById(id);

        return new ResponseEntity<>(perspectiveDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getByOrganizationId/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Set<PerspectiveDTO> getByOrganizationId(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);
        //Set<PerspectiveDTO> perspectiveDTO = perspectiveService.findPerspectivesByOrganizationId(id);

        return  perspectiveService.findPerspectivesByOrganizationId(id);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PerspectiveDTO> update(@RequestBody PerspectiveDTO perspectiveDTO) {

        perspectiveService.update(perspectiveDTO);

        return new ResponseEntity<>(perspectiveDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PerspectiveDTO> create(@RequestBody PerspectiveDTO perspectiveDTO) {

        perspectiveService.save(perspectiveDTO);

        return new ResponseEntity<>(perspectiveDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Set<PerspectiveDTO>> getAll() {
        log.debug("REST request to get all: {}");
        Set<PerspectiveDTO> perspectiveDTOs = perspectiveService.getAllPerspectives();
        return new ResponseEntity<>(perspectiveDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@RequestBody PerspectiveDTO perspectiveDTO) {
        log.debug("REST request to delete : {}", perspectiveDTO);
        perspectiveService.delete(perspectiveDTO);
    }
}
