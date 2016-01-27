package ro.teamnet.ou.web.rest;

import javax.inject.Inject;
import java.util.List;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.ou.service.OrganizationAccountService;
import ro.teamnet.ou.web.rest.dto.AccountOrganizationsDTO;

@RestController
@RequestMapping(value = "/app/rest/adminAccount")
public class AdminOrganizationalResource {

    @Inject
    private OrganizationAccountService organizationAccountService;

    @RequestMapping(value = "/allAccountsWithOrganizations", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AccountOrganizationsDTO> getAllAccountsWithOrganizations() {
        return organizationAccountService.getAllAccountsWithOrganizations();
    }

}
