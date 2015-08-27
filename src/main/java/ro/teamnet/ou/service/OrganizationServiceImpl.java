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

        Organization organization = OrganizationMapper.toJPAlazy(organizationDTO);
        organization = organizationRepository.save(organization);
//        ro.teamnet.ou.domain.neo.Organization organizationNeo = OrganizationMapper.toNeo(organizationDTO);
//        organizationNeo.setJpaId(organization.getId());
//
//        Long neoId = organizationNeoRepository.findByJpaId(organization.getId()).getId();
//        organizationNeo.setId(neoId);

//        organizationNeo = organizationNeoRepository.save(organizationNeo);

        return OrganizationMapper.toDTOLazy(organization);
    }

    @Override
    public OrganizationDTO update(OrganizationDTO organizationDTO) {

        Organization organization = OrganizationMapper.toJPA(organizationDTO);
        organization = organizationRepository.save(organization);

        return OrganizationMapper.toDTO(organization);
    }

    @Override
    public void delete(Long id) {
        Organization organization = organizationRepository.findOne(id);
        organizationRepository.delete(organization);

       // ro.teamnet.ou.domain.neo.Organization organizationNeo = organizationNeoRepository.findByJpaId(id);
       // organizationNeoRepository.delete(organizationNeo);
    }


    /**
     * Metoda ce returneaza lista cu toate OrganizationDTO, obtinuta prin combinarea datelor
     * din jpa si neo.
     * !!!!!!!!!!!!!!!! Complexitate n^2
     * @return
     */
    @Override
    public Set<OrganizationDTO> getAllOrganizationDTOs() {
        List<Organization> organizationList = organizationRepository.findAll();
//        List<ro.teamnet.ou.domain.neo.Organization> organizationListNeo = organizationNeoRepository.getAllOrganizations();
//
//        Set<OrganizationDTO> organizationDTOs = new HashSet<>();
//        if (organizationList != null && organizationListNeo != null) {
//            for (int i = 0; i < organizationList.size(); i++) {
//                for (int j=0; j < organizationListNeo.size(); j++) {
//                    if (organizationList.get(i).getId().equals(organizationListNeo.get(j).getJpaId())) {
//                        organizationDTOs.add(OrganizationMapper.toDTO(organizationList.get(i), organizationListNeo.get(j)));
//                    }
//                }
//            }
//        }

        Set<OrganizationDTO> organizationDTOs = new HashSet<>();
        for(Organization organization : organizationList){
            organizationDTOs.add(OrganizationMapper.toDTO(organization));
        }

        return organizationDTOs;
    }

    @Override
    public OrganizationDTO findOrganizationDTOById(Long id) {
        Organization organization =  organizationRepository.findOne(id);
        ro.teamnet.ou.domain.neo.Organization organizationNeo = organizationNeoRepository.findByJpaId(id);

        return OrganizationMapper.toDTO(organization, organizationNeo);
    }
}
