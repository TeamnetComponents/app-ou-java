package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.bootstrap.web.rest.dto.AccountDTO;
import ro.teamnet.ou.service.OUAccountService;
import javax.inject.Inject;
import java.util.Collection;


@RestController
@RequestMapping(value = "/app/rest/account")
public class AuthenticatedOUAccountResource {

    @Inject
    private OUAccountService ouAccountService;

    @RequestMapping(value = "/accountsForOrg/{code}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Collection<AccountDTO>> getAccountsByCode(@PathVariable String code) {
        return new ResponseEntity<>(ouAccountService.getAccountsInOrganizationalUnitByCode(code), HttpStatus.OK);
    }

}
