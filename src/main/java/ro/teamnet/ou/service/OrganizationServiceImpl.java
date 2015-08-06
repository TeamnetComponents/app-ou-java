package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;

import javax.inject.Inject;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class OrganizationServiceImpl extends AbstractServiceImpl<Organization,Long> implements OrganizationService {

    public OrganizationRepository organizationRepository;

    @Inject
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        super(organizationRepository);
        this.organizationRepository = organizationRepository;
    }
}
