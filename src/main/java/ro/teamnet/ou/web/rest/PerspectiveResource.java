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
    //extends ro.teamnet.bootstrap.web.rest.AbstractResource<PerspectiveDTO, Long>{

    private final Logger log = LoggerFactory.getLogger(PerspectiveResource.class);
    @Inject
    private PerspectiveService perspectiveService;
    @Inject
    private OrganizationService organizationService;

//    @Inject
//    public PerspectiveResource(PerspectiveService perspectiveService) {
//        super(perspectiveService);
//        this.perspectiveService = perspectiveService;
//    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PerspectiveDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);

        Perspective perspective = perspectiveService.findPerspectiveById(id);
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = perspectiveService.findPerspectiveNeoById(id);
        if(perspective == null || perspectiveNeo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PerspectiveDTO perspectiveDTO = perspectiveService.toPerspectiveDTO(perspective, perspectiveNeo);

        return new ResponseEntity<>(perspectiveDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PerspectiveDTO> update(@PathVariable Long id, @RequestBody PerspectiveDTO perspectiveDTO) {
        log.debug("REST request to update the function : {}", id);

        Perspective perspective = perspectiveService.findPerspectiveById(id);
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = perspectiveService.findPerspectiveNeoById(id);
        if (perspective == null || perspectiveNeo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        perspectiveService.update(perspective, perspectiveNeo, perspectiveDTO);
//        perspectiveService.updatePerspective(perspective, perspectiveDTO);
//        perspectiveService.updatePerspectiveNeo(perspectiveNeo, perspectiveDTO);
//        perspectiveService.toPerspectiveDTO(perspective, perspectiveNeo);

        return new ResponseEntity<>(perspectiveDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Set<PerspectiveDTO>> getAll() {
        log.debug("REST request to get all: {}");

        List<Perspective> perspectiveList = perspectiveService.getAllPerspectives();
        List<ro.teamnet.ou.domain.neo.Perspective> perspectiveListNeo = perspectiveService.getAllPerspectivesNeo();
        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        if(perspectiveList != null && perspectiveListNeo != null) {
            for (int i = 0; i < perspectiveList.size(); i++) {
                perspectiveDTOs.add(perspectiveService.toPerspectiveDTO(perspectiveList.get(i), perspectiveListNeo.get(i)));
            }
        }

        return new ResponseEntity<Set<PerspectiveDTO>>(perspectiveDTOs, HttpStatus.OK);
    }
}
