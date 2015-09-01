package ro.teamnet.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.teamnet.ou.service.OUAccountService;
import ro.teamnet.ou.service.OrganizationalUnitService;
import ro.teamnet.ou.web.rest.dto.AccountDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Radu.Hoaghe on 8/3/2015.
 */
@RestController
@RequestMapping("/app/rest/organizationalUnit")
public class OrganizationalUnitResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationalUnitResource.class);
    @Inject
    private OrganizationalUnitService organizationalUnitService;
    @Inject
    private OUAccountService ouAccountService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Set<OrganizationalUnitDTO>> getAll() {
        log.debug("REST request to get all");
        return new ResponseEntity<>(organizationalUnitService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationalUnitDTO> get(@PathVariable Long id) {
        log.debug("REST request to get  : {}", id);
        OrganizationalUnitDTO organizationalUnit = organizationalUnitService.findOne(id);
        if (organizationalUnit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizationalUnit, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationalUnitDTO> create(@RequestBody OrganizationalUnitDTO organizationalUnitDTO) {
        log.debug("REST request to create a new organizational unit");
        return new ResponseEntity<>(organizationalUnitService.save(organizationalUnitDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationalUnitDTO> update(@RequestBody OrganizationalUnitDTO organizationalUnitDTO) {
        log.debug("REST request to update the organizational unit : {}", organizationalUnitDTO.getId());
        return new ResponseEntity<>(organizationalUnitService.save(organizationalUnitDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete : {}", id);
        organizationalUnitService.delete(id);
    }

    @RequestMapping(value = "/getTree/{rootId}",
            method = RequestMethod.GET)
    @Timed
    public String getTree(@PathVariable Long rootId) {
        return organizationalUnitService.getTree(rootId);
    }

    @RequestMapping(value = "/accounts/{ouId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AccountDTO> getAccounts(@PathVariable Long ouId){
        return ouAccountService.getAccountsInOrganizationalUnit(ouId);
    }

    @RequestMapping(value = "/eligibleAccounts/{ouId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AccountDTO> getEligibleAccounts(@PathVariable Long ouId){
        return ouAccountService.getAccountsEligibleForOrganizationalUnit(ouId);}

    @RequestMapping(value = "/getParentOrgUnitsById/{rootId}/{id}",
            method = RequestMethod.GET)
    @Timed
    List<OrganizationalUnitDTO> getParentOrgUnitsById(@PathVariable Long rootId, @PathVariable Long id) {
        return organizationalUnitService.getParentOrgUnitsById(rootId, id);
    }
}