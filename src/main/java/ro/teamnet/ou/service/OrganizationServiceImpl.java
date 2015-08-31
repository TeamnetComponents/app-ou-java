package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.mapper.OrganizationMapper;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;
import ro.teamnet.ou.repository.neo.OrganizationNeoRepository;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Inject
    public OrganizationRepository organizationRepository;

    @Inject
    public OrganizationNeoRepository organizationNeoRepository;

    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        Organization organization = OrganizationMapper.toJPA(organizationDTO, true);
        organization = organizationRepository.save(organization);
        organizationDTO.setId(organization.getId());
        saveNeo(organizationDTO);
        return OrganizationMapper.toDTO(organization, true);
    }

    private void saveNeo(OrganizationDTO organizationDTO) {
        ro.teamnet.ou.domain.neo.Organization neoOrganization = OrganizationMapper.toNeo(organizationDTO);
        ro.teamnet.ou.domain.neo.Organization existingNeoOrganization = organizationNeoRepository.findByJpaId(organizationDTO.getId());
        if (existingNeoOrganization != null) {
            neoOrganization.setId(existingNeoOrganization.getId());
        }
        organizationNeoRepository.save(neoOrganization);
    }

    @Override
    public OrganizationDTO update(OrganizationDTO organizationDTO) {
        Organization organization = OrganizationMapper.toJPA(organizationDTO);
        organization = organizationRepository.save(organization);
        saveNeo(organizationDTO);
        return OrganizationMapper.toDTO(organization);
    }

    @Override
    public void delete(Long id) {
        Organization organization = organizationRepository.findOne(id);
        organizationRepository.delete(organization);

       // ro.teamnet.ou.domain.neo.Organization organizationNeo = organizationNeoRepository.findByJpaId(id);
       // organizationNeoRepository.delete(organizationNeo);
    }


    @Override
    public Set<OrganizationDTO> getAllOrganizationDTOs() {
        Set<OrganizationDTO> organizationDTOs = new HashSet<>();
        for(Organization organization : organizationRepository.findAll()){
            organizationDTOs.add(OrganizationMapper.toDTO(organization));
        }
        return organizationDTOs;
    }

    @Override
    public OrganizationDTO findOrganizationDTOById(Long id) {
        return OrganizationMapper.toDTO(organizationRepository.findOne(id));
    }
}
