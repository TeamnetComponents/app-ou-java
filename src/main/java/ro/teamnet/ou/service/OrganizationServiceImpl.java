package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.extend.AppPage;
import ro.teamnet.bootstrap.extend.AppPageable;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.ou.domain.Organization;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;

import java.util.List;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public class OrganizationServiceImpl extends AbstractServiceImpl<Organization,Long> implements OrganizationService {

    public OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        super(organizationRepository);
        this.organizationRepository = organizationRepository;
    }
}
