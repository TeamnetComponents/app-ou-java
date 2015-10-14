package ro.teamnet.ou.acl.aop;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.ou.acl.service.OrganizationalUnitHierarchyFilterAdvice;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Marian.Spoiala on 10/10/2015.
 */
@Service
public class OrgUnitFilterPlugin implements OUFilterPlugin {

    @Inject
    OrganizationalUnitHierarchyFilterAdvice organizationalUnitHierarchyFilterAdvice;

    private Boolean hasPermissionForAll(UserDetails authenticatedUser) {
        for (GrantedAuthority grantedAuthority : authenticatedUser.getAuthorities()) {
            if (grantedAuthority instanceof ModuleRight) {
                ModuleRight moduleRight = (ModuleRight) grantedAuthority;
                if (moduleRight.getModule().getCode().equals("ALL_OU_ACCESS")) {
                    return true;
                }
            }
        }

        return false;
    }

    private Boolean checkIfBelongs(Collection<Long> ids, Long checkId) {
        for (Long id : ids) {
            if (id.equals(checkId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean isObjectAllowed(Object obj) {
        //By default if not recognized, it will not be filtered
        if (!(obj instanceof OrganizationalUnitDTO))
            return true;

        OrganizationalUnitDTO organizationalUnitDTO = (OrganizationalUnitDTO) obj;

        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();

        if (hasPermissionForAll(authenticatedUser))
            return true;

        Collection<Long> orgUnitJpaIds = organizationalUnitHierarchyFilterAdvice.getAuthenticatedUserOUIds();
        Long orgUnitJpaId = organizationalUnitDTO.getId();

        if (checkIfBelongs(orgUnitJpaIds, orgUnitJpaId))
            return true;

        return false;
    }

    @Override
    public Object filterObjects(Object obj) {

        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();

        Collection<Long> authOrgUnitJpaIds = organizationalUnitHierarchyFilterAdvice.getAuthenticatedUserOUIds();

        if (hasPermissionForAll(authenticatedUser))
            return obj;

        if (obj instanceof Set) {
            Set<OrganizationalUnitDTO> orgUnitDTOs = (Set<OrganizationalUnitDTO>) obj;
            Set<OrganizationalUnitDTO> finalOrgUnitDTOs = new HashSet<>();
            for (OrganizationalUnitDTO orgUnit : orgUnitDTOs) {
                if (checkIfBelongs(authOrgUnitJpaIds, orgUnit.getId())) {
                    finalOrgUnitDTOs.add(orgUnit);
                }
            }
            return finalOrgUnitDTOs;
        } else if (obj instanceof List) {
            List<OrganizationalUnitDTO> orgUnitDTOs = (List<OrganizationalUnitDTO>) obj;
            List<OrganizationalUnitDTO> finalOrgUnitDTOs = new ArrayList<>();
            for (OrganizationalUnitDTO orgUnit : orgUnitDTOs) {
                if (checkIfBelongs(authOrgUnitJpaIds, orgUnit.getId())) {
                    finalOrgUnitDTOs.add(orgUnit);
                }
            }
            return finalOrgUnitDTOs;
        }

        return obj;
    }

    @Override
    public Boolean isObjectSaveAllowed(Object obj) {
        //By default if not recognized, it will not be filtered
        if (!(obj instanceof Object[]))
            return true;

        Object[] args = (Object[]) obj;

        if (args.length == 1 && args[0] instanceof OrganizationalUnitDTO) {
            OrganizationalUnitDTO organizationalUnitDTO = (OrganizationalUnitDTO) args[0];
            return this.isObjectAllowed(organizationalUnitDTO);
        }

        return false;
    }

    @Override
    public Boolean isObjectDeleteAllowed(Object obj) {
        //By default if not recognized, it will not be filtered
        if (!(obj instanceof Object[]))
            return true;

        Object[] args = (Object[]) obj;

        if (args.length == 1 && (args[0] instanceof Long || args[0] instanceof Integer)) {
            OrganizationalUnitDTO organizationalUnitDTO = (OrganizationalUnitDTO) args[0];
            return this.isObjectAllowed(organizationalUnitDTO);
        }

        return false;
    }

    @Override
    public boolean supports(String filterValue) {
        return (filterValue.equals("ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO"));
    }

}
