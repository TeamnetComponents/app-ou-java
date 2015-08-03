package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.ou.domain.OrganizationalUnit;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;

import javax.inject.Inject;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public class OrganizationalUnitServiceImpl extends AbstractServiceImpl<OrganizationalUnit, Long> implements OrganizationalUnitService {

    private OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    public OrganizationalUnitServiceImpl(OrganizationalUnitRepository organizationalUnitRepository) {
        super(organizationalUnitRepository);
        this.organizationalUnitRepository = organizationalUnitRepository;
    }
}
