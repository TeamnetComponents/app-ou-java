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
 * Created by Marian.Spoiala on 10/19/2015.
 */
@RestController
@RequestMapping("/app/rest/accountfunction")
public class AccountFunctionResource {

    private final Logger log = LoggerFactory.getLogger(AccountFunctionResource.class);

    @Inject
    private FunctionService functionService;

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

    @RequestMapping(value = "/function/delete/{accountId}/{functionId}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity removeFromAccount(@PathVariable Long accountId, @PathVariable Long functionId) {
        functionService.removeFromAccount(accountId, functionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
