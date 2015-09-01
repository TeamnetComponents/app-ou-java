package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.ou.service.FunctionService;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import javax.inject.Inject;
import java.util.Set;

/**
 * REST controller for managing function.
 */
@RestController
@RequestMapping("/app/rest/function")
public class FunctionResource{

    private final Logger log = LoggerFactory.getLogger(FunctionResource.class);
    private FunctionService functionService;

    @Inject
    public FunctionResource(FunctionService functionService) {
        this.functionService=functionService;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Set<FunctionDTO> getAll() {
        log.debug("REST request to get all functions fetching module rights also");
        return functionService.findAll();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FunctionDTO> get(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);
        FunctionDTO functionDto = functionService.findOne(id);
        if (functionDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(functionDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FunctionDTO> create(@RequestBody FunctionDTO functionDTO) {
        return new ResponseEntity<>(functionService.save(functionDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FunctionDTO> update(@RequestBody FunctionDTO functionDTO) {
        return new ResponseEntity<>(functionService.save(functionDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete : {}", id);
        functionService.delete(id);
    }

    @RequestMapping(value = "/account/{accountId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Set<FunctionDTO> getAllByAccountId(@PathVariable Long accountId) {
        return functionService.findAllByAccountId(accountId);
    }

    @RequestMapping(value = "/account/{accountId}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity addToAccount(@PathVariable Long accountId, @RequestBody FunctionDTO functionDTO) {
        functionService.addToAccount(accountId, functionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/{accountId}/{functionId}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity removeFromAccount(@PathVariable Long accountId, @PathVariable Long functionId) {
        functionService.removeFromAccount(accountId, functionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ou/{ouId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Set<FunctionDTO> getAllByOrganizationalUnitId(@PathVariable Long ouId) {
        return functionService.findAllByOrganizationalUnitId(ouId);
    }

    @RequestMapping(value = "/ou/{ouId}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity addToOrganizationalUnit(@PathVariable Long ouId, @RequestBody FunctionDTO functionDTO) {
        functionService.addToOrganizationalUnit(ouId, functionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ou/{ouId}/{functionId}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity removeFromOrganizationalUnit(@PathVariable Long ouId, @PathVariable Long functionId) {
        functionService.removeFromOrganizationalUnit(ouId, functionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
