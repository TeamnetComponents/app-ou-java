package ro.teamnet.ou.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.ou.domain.Organization;
import ro.teamnet.ou.service.OrganizationService;

import javax.inject.Inject;

/**
 * Created by Radu.Hoaghe on 8/3/2015.
 */
@RestController
@RequestMapping("/app/rest/organization")
public class OrganizationResource extends ro.teamnet.bootstrap.web.rest.AbstractResource<Organization,Long>{

    private OrganizationService organizationService;

    @Inject
    public OrganizationResource(OrganizationService organizationService) {
        super(organizationService);
        this.organizationService = organizationService;
    }
}
