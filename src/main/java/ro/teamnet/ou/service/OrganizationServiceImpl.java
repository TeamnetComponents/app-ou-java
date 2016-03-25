package ro.teamnet.ou.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.security.UserExtension;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.bootstrap.web.rest.dto.AccountDTO;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mapper.OrganizationMapper;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;
import ro.teamnet.ou.repository.neo.OrganizationNeoRepository;
import ro.teamnet.ou.security.UserOrganizationalUnitDetails;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional(value="jpaTransactionManager", readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private OrganizationNeoRepository organizationNeoRepository;

    @Inject
    private OrganizationalUnitService organizationalUnitService;

    @Override
    @Transactional(value="crossStoreTransactionManager")
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
    @Transactional(value="crossStoreTransactionManager")
    public OrganizationDTO update(OrganizationDTO organizationDTO) {
        Organization organization = OrganizationMapper.toJPA(organizationDTO);
        organization = organizationRepository.save(organization);
        saveNeo(organizationDTO);
        return OrganizationMapper.toDTO(organization);
    }

    @Override
    @Transactional(value="jpaTransactionManager")
    public void delete(Long id) {
        Organization organization = organizationRepository.findOne(id);
        organizationRepository.delete(organization);

        // ro.teamnet.ou.domain.neo.Organization organizationNeo = organizationNeoRepository.findByJpaId(id);
        // organizationNeoRepository.delete(organizationNeo);
    }


    @Override
    public Set<OrganizationDTO> getAllOrganizationDTOs() {
        Set<OrganizationDTO> organizationDTOs = new HashSet<>();
        for (Organization organization : organizationRepository.findAll()) {
            organizationDTOs.add(OrganizationMapper.toDTO(organization));
        }
        return organizationDTOs;
    }

    @Override
    public OrganizationDTO findOrganizationDTOById(Long id) {
        return OrganizationMapper.toDTO(organizationRepository.findOne(id));
    }

    @Override
    public Collection<OrganizationDTO> getPublicOrganizations() {
        return getAllOrganizationDTOs();
    }

    @Override
    @Transactional(value="crossStoreTransactionManager")
    public Set<OrganizationalUnit> getOUsInOrganization(Long organizationId) {
        Set<OrganizationalUnit> organizationalUnits = new HashSet<>();
        for (OrganizationalUnit organizationalUnit : organizationNeoRepository.findByJpaId(organizationId).getRoots()) {
            organizationalUnits.addAll(organizationalUnitService.getOrganizationalUnitTreeById(organizationalUnit.getId()));
        }
        return organizationalUnits;
    }

    @Override
    public OrganizationDTO findOrganizationDTOByOrgUnitId(Long jpaId) {
        return OrganizationMapper.toDTO(organizationNeoRepository.findByOrganizationalUnitJpaId(jpaId));
    }

    @Override
    public Collection<OrganizationDTO> getOrganizationsForCurrentUser() {
        Set<OrganizationDTO> organizations = new HashSet<>();
        String username = SecurityUtils.getAuthenticatedUser().getUsername();
        for (ro.teamnet.ou.domain.neo.Organization organization : organizationNeoRepository.findByUsername(username)) {
            organizations.add(OrganizationMapper.toDTO(organizationRepository.findOne(organization.getJpaId()), true));
        }
        return organizations;
    }

    @Override
    public AccountDTO getCurrentUserWithOuAuthorities() {
        UserDetails userDetails = SecurityUtils.getAuthenticatedUser();
        UserOrganizationalUnitDetails ouExtensions = null;

        if (userDetails instanceof UserExtension) {
            Object obj = ((UserExtension) userDetails).getExtensions().get("UserOrganizationalUnitDetails");
            if (obj != null && obj instanceof UserOrganizationalUnitDetails) {
                ouExtensions = (UserOrganizationalUnitDetails) obj;
            }
        }

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(ouExtensions==null){
            return null;
        }
        Collection<Function> functions = ouExtensions.getFunctions();

        for (Function function : functions) {
            grantedAuthorities.addAll(function.getModuleRights());
        }

        return new AccountDTO(userDetails, grantedAuthorities);

    }

    @Override
    public OrganizationDTO findByCode(String code) {
        Organization organization = organizationRepository.findByCode(code);
        if (organization == null) {
            return null;
        }
        return OrganizationMapper.toDTO(organizationRepository.findByCode(code));
    }
}
