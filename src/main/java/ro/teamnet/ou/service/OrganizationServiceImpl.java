package ro.teamnet.ou.service;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mapper.OrganizationMapper;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.OrganizationNeoRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.repository.neo.PerspectiveNeoRepository;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
//        extends AbstractServiceImpl<Organization,Long> implements OrganizationService {

    @Inject
    public OrganizationRepository organizationRepository;
    @Inject
    public OrganizationNeoRepository organizationNeoRepository;
    @Inject
    public PerspectiveRepository perspectiveRepository;
    @Inject
    public PerspectiveNeoRepository perspectiveNeoRepository;
    @Inject
    public OrganizationalUnitRepository organizationalUnitRepository;
    @Inject
    public OrganizationalUnitNeoRepository organizationalUnitNeoRepository;
    @Inject
    public PerspectiveService perspectiveService;
    @Inject
    public OrganizationalUnitService organizationalUnitService;

//    @Inject
//    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
//        super(organizationRepository);
//        this.organizationRepository = organizationRepository;
//    }

    @Override
    public OrganizationDTO createOrganizationDTO(Long id, String code, String description, Date validFrom, Date validTo, Set<PerspectiveDTO> perspectiveDTOSet) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(id);
        organizationDTO.setCode(code);
        organizationDTO.setDescription(description);
        organizationDTO.setValidFrom(validFrom);
        organizationDTO.setValidTo(validTo);
        organizationDTO.setPerspectives(perspectiveDTOSet);

        return organizationDTO;
    }

    @Override
    @Transactional
    public OrganizationDTO toOrganizationDTO(Organization organization, ro.teamnet.ou.domain.neo.Organization organizationNeo) {

        OrganizationDTO organizationDTO = OrganizationMapper.from(organization, organizationNeo);
        return organizationDTO;
    }

    @Override
//    @Transactional
    public Organization updateOrganization(Organization organization, OrganizationDTO organizationDTO) {

        organization = OrganizationMapper.from(organizationDTO);
        return organizationRepository.save(organization);
    }

    @Override
//    @Transactional
    public ro.teamnet.ou.domain.neo.Organization updateOrganizationNeo(ro.teamnet.ou.domain.neo.Organization organization, OrganizationDTO organizationDTO) {

        organization = OrganizationMapper.fromNeo(organizationDTO);
        return organizationNeoRepository.save(organization);
    }

    @Override
    @Transactional
    public Organization findOrganizationById(Long id) {
        return organizationRepository.findOne(id);
    }

    @Override
    @Transactional
    public ro.teamnet.ou.domain.neo.Organization findOrganizationNeoById(Long id) {
        return organizationNeoRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<Organization> getAllOrganization() {
        return organizationRepository.findAll();
    }

    @Override
    @Transactional
    public List<ro.teamnet.ou.domain.neo.Organization> gelAllOrganizationNeo() {
        Result<ro.teamnet.ou.domain.neo.Organization> result = organizationNeoRepository.findAll();
        List<ro.teamnet.ou.domain.neo.Organization> organizationList = new ArrayList<>();
        for (ro.teamnet.ou.domain.neo.Organization organization : result) {
            organizationList.add(organization);
        }
        return organizationList;
    }

    @Override
//    @Transactional
    public OrganizationDTO create(OrganizationDTO organizationDTO) {

        Organization organization = new Organization();
        organization = this.updateOrganization(organization, organizationDTO);
        ro.teamnet.ou.domain.neo.Organization organizationNeo = new ro.teamnet.ou.domain.neo.Organization();
        organizationDTO.setJpaId(organization.getId());
        organizationNeo = this.updateOrganizationNeo(organizationNeo, organizationDTO);

        return this.toOrganizationDTO(organization, organizationNeo);
    }

    @Override
//    @Transactional
    public OrganizationDTO update(Organization organization, ro.teamnet.ou.domain.neo.Organization organizationNeo, OrganizationDTO organizationDTO) {

        this.updateOrganization(organization, organizationDTO);
        this.updateOrganizationNeo(organizationNeo, organizationDTO);

        return this.toOrganizationDTO(organization, organizationNeo);
    }
}
