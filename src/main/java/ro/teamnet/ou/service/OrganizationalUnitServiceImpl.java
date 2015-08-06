package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;

import javax.inject.Inject;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class OrganizationalUnitServiceImpl extends AbstractServiceImpl<OrganizationalUnit, Long> implements OrganizationalUnitService {

    private final OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    public OrganizationalUnitServiceImpl(OrganizationalUnitRepository organizationalUnitRepository) {
        super(organizationalUnitRepository);
        this.organizationalUnitRepository = organizationalUnitRepository;
    }
}
