package ro.teamnet.ou.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.ou.domain.OrganizationalUnit;
import ro.teamnet.ou.service.OrganizationalUnitService;

import javax.inject.Inject;

/**
 * Created by Radu.Hoaghe on 8/3/2015.
 */
@RestController
@RequestMapping("/app/rest/organizationalUnit")
public class OrganizationalUnitResource extends ro.teamnet.bootstrap.web.rest.AbstractResource<OrganizationalUnit,Long>{

    private OrganizationalUnitService organizationalUnitService;

    @Inject
    public OrganizationalUnitResource(OrganizationalUnitService organizationalUnitService) {
        super(organizationalUnitService);
        this.organizationalUnitService = organizationalUnitService;
    }
}
