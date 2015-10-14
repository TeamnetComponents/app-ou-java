package ro.teamnet.ou.acl.aop;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.acl.service.OrganizationalUnitHierarchyFilterAdvice;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Marian.Spoiala on 10/10/2015.
 */
@Service
public class OrganizationFilterPlugin implements OUFilterPlugin {

    @Inject
    OrganizationService organizationService;

    @Inject
    AccountService accountService;

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

    private Boolean checkIfBelongs(Collection<OrganizationDTO> orgDTOs, Long checkId) {
        for (OrganizationDTO orgDTO : orgDTOs) {
            if (orgDTO.getId().equals(checkId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean isObjectAllowed(Object obj) {
        //By default if not recognized, it will not be filtered
        if (!(obj instanceof OrganizationDTO))
            return true;

        OrganizationDTO organizationDTO = (OrganizationDTO) obj;

        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();

        if (hasPermissionForAll(authenticatedUser))
            return true;

        Collection<OrganizationDTO> orgDTOs = organizationService.getOrganizationsForCurrentUser();
        Long checkOrgJpaId = organizationDTO.getId();

        if (checkIfBelongs(orgDTOs, checkOrgJpaId)) ;

        return false;
    }

    @Override
    public Object filterObjects(Object obj) {
        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();

        if (hasPermissionForAll(authenticatedUser))
            return obj;

        Collection<OrganizationDTO> orgDTOs = organizationService.getOrganizationsForCurrentUser();

        if (obj instanceof Set) {
            Set<OrganizationDTO> orgUnitDTOs = (Set<OrganizationDTO>) obj;
            Set<OrganizationDTO> finalOrgUnitDTOs = new HashSet<>();
            for (OrganizationDTO orgUnit : orgUnitDTOs) {
                if (checkIfBelongs(orgDTOs, orgUnit.getId())) {
                    finalOrgUnitDTOs.add(orgUnit);
                }
            }
            return finalOrgUnitDTOs;
        } else if (obj instanceof List) {
            List<OrganizationDTO> orgUnitDTOs = (List<OrganizationDTO>) obj;
            List<OrganizationDTO> finalOrgUnitDTOs = new ArrayList<>();
            for (OrganizationDTO orgUnit : orgUnitDTOs) {
                if (checkIfBelongs(orgDTOs, orgUnit.getId())) {
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

        /*if (args.length == 1 && args[0] instanceof OrganizationalUnitDTO) {
            OrganizationalUnitDTO organizationalUnitDTO = (OrganizationalUnitDTO) args[0];
            return this.isObjectAllowed(organizationalUnitDTO);
        }*/

        return false;
    }

    @Override
    public Boolean isObjectDeleteAllowed(Object obj) {
        //By default if not recognized, it will not be filtered
        if (!(obj instanceof Object[]))
            return true;

        Object[] args = (Object[]) obj;

        /*if (args.length == 1 && (args[0] instanceof Long || args[0] instanceof Integer)) {
            OrganizationalUnitDTO organizationalUnitDTO = (OrganizationalUnitDTO) args[0];
            return this.isObjectAllowed(organizationalUnitDTO);
        }*/

        return false;
    }

    @Override
    public boolean supports(String filterValue) {
        return (filterValue.equals("ro.teamnet.ou.web.rest.dto.OrganizationDTO"));
    }

}
