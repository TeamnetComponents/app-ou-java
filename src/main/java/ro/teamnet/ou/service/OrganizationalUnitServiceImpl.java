package ro.teamnet.ou.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.domain.ApplicationRole;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.acl.service.OrganizationalUnitHierarchyFilterAdvice;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.mapper.OrganizationalUnitMapper;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Marian.Spoiala 20.08.2015
 */
@Service
@Transactional
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

    @Inject
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    @Inject
    private PerspectiveRepository perspectiveRepository;

    @Inject
    private AccountService accountService;

    @Inject
    private OUAccountService ouAccountService;

    @Inject
    private OrganizationalUnitHierarchyFilterAdvice filterAdvice;

    //TODO : Ce se intampla daca nu merge nici salvarea in neo?
    @Override
    public OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO) {
        organizationalUnitDTO = saveJPA(organizationalUnitDTO);
        if (organizationalUnitDTO != null)
            saveNeo(organizationalUnitDTO);
        return organizationalUnitDTO;
    }

    @Override
    public OrganizationalUnitDTO saveOUTreeRoot(PerspectiveDTO perspective) {
        OrganizationalUnit ouTreeRoot = new OrganizationalUnit();
        ouTreeRoot.setPerspective(perspectiveRepository.findOne(perspective.getId()));
        ouTreeRoot.setCode(perspective.getCode() + "_ROOT");
        ouTreeRoot.setActive(true);
        organizationalUnitRepository.save(ouTreeRoot);
        OrganizationalUnitDTO organizationalUnitDTO = OrganizationalUnitMapper.toDTO(ouTreeRoot);
        saveNeo(organizationalUnitDTO);
        return organizationalUnitDTO;
    }

    private void saveNeo(OrganizationalUnitDTO organizationalUnitDTO) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit = OrganizationalUnitMapper.toNeo(organizationalUnitDTO);

        if (organizationalUnitDTO.getParent() != null) {
            organizationalUnit.setParent(organizationalUnitNeoRepository.findByJpaId(organizationalUnitDTO.getParent().getId()));
        }
        ro.teamnet.ou.domain.neo.OrganizationalUnit existingNeoOu = organizationalUnitNeoRepository.findByJpaId(organizationalUnitDTO.getId());
        if (existingNeoOu != null) {
            organizationalUnit.setId(existingNeoOu.getId());
        }
        organizationalUnitNeoRepository.save(organizationalUnit);
    }

    /**
     * Atentie! Pe Organizational Unit DTO nu vin si AccountFunction! Mapperul nu va avea cum sa le adauge si rezulta
     * buguri!
     *
     * @param organizationalUnitDTO
     * @return
     */
    private OrganizationalUnitDTO saveJPA(OrganizationalUnitDTO organizationalUnitDTO) {
        OrganizationalUnit organizationalUnit = OrganizationalUnitMapper.toJPA(organizationalUnitDTO);
        if (organizationalUnitDTO.getId() != null) {
            OrganizationalUnit currentOrganizationalUnit = organizationalUnitRepository.findOne(organizationalUnitDTO.getId());
            organizationalUnit.setAccountFunctions(currentOrganizationalUnit.getAccountFunctions());
        }
        organizationalUnitRepository.save(organizationalUnit);
        organizationalUnitDTO.setId(organizationalUnit.getId());
        return organizationalUnitDTO;
    }

    @Override
    public void delete(Long id) {
        organizationalUnitRepository.delete(id);

        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitNeoRepository.findByJpaId(id);
        if (organizationalUnitNeo != null) {
            organizationalUnitNeoRepository.deleteNodeAndChildren(organizationalUnitNeo.getId());
        }
    }

    @Override
    public OrganizationalUnitDTO findOne(Long id) {
        OrganizationalUnit organizationalUnit = organizationalUnitRepository.findOne(id);
        return OrganizationalUnitMapper.toDTO(organizationalUnit);
    }

    @Override
    public Set<OrganizationalUnitDTO> findAll() {
        List<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.findAll();
        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitsNeo = organizationalUnitNeoRepository.getAllOrganizationalUnits();

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        if (organizationalUnits != null && organizationalUnitsNeo != null) {
            for (OrganizationalUnit organizationalUnit : organizationalUnits) {
                for (ro.teamnet.ou.domain.neo.OrganizationalUnit neoOu : organizationalUnitsNeo) {
                    if (organizationalUnit.getId().equals(neoOu.getJpaId())) {
                        organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit));
                    }
                }
            }
        }

        return organizationalUnitDTOs;
    }

    @Override
    public Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> getOrganizationalUnitTreeById(Long id) {
        return organizationalUnitNeoRepository.getOrganizationalUnitTreeById(id);
    }





    public List<OrganizationalUnitDTO> getParentOrgUnitsById(Long rootId, Long id) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit rootOu = organizationalUnitNeoRepository.findByJpaId(rootId);
        ro.teamnet.ou.domain.neo.OrganizationalUnit nodeOu = organizationalUnitNeoRepository.findByJpaId(id);

        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> orgUnitSet = organizationalUnitNeoRepository.getParentOrgUnitsById(rootOu.getId(), nodeOu.getId());

        List<OrganizationalUnitDTO> organizationalUnitDTOs = new ArrayList<>();
        for (ro.teamnet.ou.domain.neo.OrganizationalUnit orgUnit : orgUnitSet) {
            organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(orgUnit, false));
        }

        return organizationalUnitDTOs;
    }

    @Override
    public Collection<OrganizationalUnitDTO> getPublicOUsByOrganizationId(Long organizationId) {
        //TODO
        return Collections.emptySet();
    }

    @Override
    public Collection<OrganizationalUnitDTO> getOUsForCurrentUser() {
        return getOUsForUser(SecurityUtils.getAuthenticatedUser().getUsername());
    }

    @Override
    public Collection<OrganizationalUnitDTO> getOUsForCurrentUser(Long organizationId) {
        return getOUsForUser(SecurityUtils.getAuthenticatedUser().getUsername(), organizationId);
    }

    @Override
    public Collection<OrganizationalUnitDTO> getOUsForUser(String username) {
        Account account = accountService.findByLogin(username);
        return ouAccountService.getOrganizationalUnits(account.getId());
    }

    @Override
    public Collection<OrganizationalUnitDTO> getOUsForUser(String username, Long organizationId) {
        if (organizationId == null) {
            return getOUsForUser(username);
        }
        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        for (ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit : organizationalUnitNeoRepository.
                getOrganizationalUnitsByUsernameAndOrganization(username, organizationId)) {
            organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit, true));
        }
        return organizationalUnitDTOs;
    }

    @Override
    public Set<Long> getOrgUnitSubTreeJpaIdsByRootJpaId(Long jpaId) {
        return organizationalUnitNeoRepository.getOrganizationalUnitSubTreeJpaIdsByRootJpaId(jpaId);
    }

    @Override
    public Boolean filterOrgUnitByUserRights(Long orgUnitJpaId) {

        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();

        for (GrantedAuthority grantedAuthority : authenticatedUser.getAuthorities()) {
            if (grantedAuthority instanceof ApplicationRole) {
                ApplicationRole applicationRole = (ApplicationRole) grantedAuthority;
                if (applicationRole.getCode().equals("ROLE_ADMIN")) {
                    return true;
                }
            }
        }

        Collection<Long> orgUnitJpaIds = filterAdvice.getAuthenticatedUserOUIds();
        for (Long x : orgUnitJpaIds) {
            if (x.equals(orgUnitJpaId)) {
                return true;
            }
        }
        return false;
    }

}
