package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.service.FunctionService;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import javax.inject.Inject;
import java.util.Set;

/**
 * REST controller for managing function.
 */
@RestController
@RequestMapping("/app/rest/function")
public class FunctionResource extends ro.teamnet.bootstrap.web.rest.AbstractResource<Function,Long>{

    private final Logger log = LoggerFactory.getLogger(FunctionResource.class);
    private FunctionService functionService;

    @Inject
    public FunctionResource(FunctionService functionService) {
        super(functionService);
        this.functionService=functionService;
    }

    @RequestMapping(value = "/allWithModuleRights", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Set<Function> getAllWithModuleRights() {
        log.debug("REST request to get all functions fetching module rights olso");
        return functionService.getAllWithModuleRights();
    }

    /**
     * POST  /rest/function  update function
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Function> updateById(@PathVariable Long id,@RequestBody FunctionDTO functionDTO) {
        log.debug("REST request to update the function : {}", id);

//        Boolean functionFound = functionService.updateFunctionById(id, functionDTO);
//        if (functionFound == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

//      we need function with module rights to be returned, for menu administration
        Function function = functionService.getOneById(id);
        if(function == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        functionService.update(function, functionDTO);

        return new ResponseEntity<>(function, HttpStatus.OK);
    }


    @Override
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Function> get(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);
        Function function = functionService.getOneById(id);
        if (function == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(function, HttpStatus.OK);
    }
}
