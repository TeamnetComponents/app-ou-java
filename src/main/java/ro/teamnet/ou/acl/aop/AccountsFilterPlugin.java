package ro.teamnet.ou.acl.aop;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.service.OrganizationAccountService;
import ro.teamnet.ou.web.rest.dto.AccountDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Marian.Spoiala on 10/13/2015.
 */
@Service
public class AccountsFilterPlugin implements OUFilterPlugin {

    @Inject
    OrganizationAccountService organizationAccountService;

    @Inject
    AccountService accountService;

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

    private Boolean checkIfBelongs(Set<Organization> authUserOrganizations, Set<Organization> reqUserOrganizations) {
        for (Organization reqUserorganization : reqUserOrganizations) {
            for (Organization authUserOrganization : authUserOrganizations) {
                if (authUserOrganization.getId().equals(reqUserorganization.getId())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Boolean isObjectAllowed(Object obj) {
        //By default if not recognized, it will not be filtered
        if (!(obj instanceof AccountDTO))
            return true;

        AccountDTO accountDTO = (AccountDTO) obj;

        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();

        if (hasPermissionForAll(authenticatedUser))
            return true;

        Set<Organization> authUserOrganizations = organizationAccountService.getOrgsByAccountUsername(authenticatedUser.getUsername());
        Set<Organization> reqUserOrganizations = organizationAccountService.getOrgsByAccountId(accountDTO.getId());

        if (checkIfBelongs(authUserOrganizations, reqUserOrganizations))
            return true;

        return false;
    }

    @Override
    public Object filterObjects(Object obj) {
        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();

        if (hasPermissionForAll(authenticatedUser))
            return obj;

        Set<Organization> authUserOrganizations = organizationAccountService.getOrgsByAccountUsername(authenticatedUser.getUsername());
        Set<Organization> reqUserOrganizations;

        if (obj instanceof Set) {
            Set<AccountDTO> accountDTOs = (Set<AccountDTO>) obj;
            Set<AccountDTO> finalOrgUnitDTOs = new HashSet<>();
            for (AccountDTO accountDTO : accountDTOs) {
                reqUserOrganizations = organizationAccountService.getOrgsByAccountId(accountDTO.getId());
                if (checkIfBelongs(authUserOrganizations, reqUserOrganizations)) {
                    finalOrgUnitDTOs.add(accountDTO);
                }
            }
            return finalOrgUnitDTOs;
        } else if (obj instanceof List) {
            List<AccountDTO> accountDTOs = (List<AccountDTO>) obj;
            List<AccountDTO> finalOrgUnitDTOs = new ArrayList<>();
            for (AccountDTO accountDTO : accountDTOs) {
                reqUserOrganizations = organizationAccountService.getOrgsByAccountId(accountDTO.getId());
                if (checkIfBelongs(authUserOrganizations, reqUserOrganizations)) {
                    finalOrgUnitDTOs.add(accountDTO);
                }
            }
            return finalOrgUnitDTOs;
        } else if (obj instanceof Collection) {
            Set<AccountDTO> accountDTOs = new HashSet<AccountDTO>((Collection) obj);
            Set<AccountDTO> finalOrgUnitDTOs = new HashSet<>();
            for (AccountDTO accountDTO : accountDTOs) {
                reqUserOrganizations = organizationAccountService.getOrgsByAccountId(accountDTO.getId());
                if (checkIfBelongs(authUserOrganizations, reqUserOrganizations)) {
                    finalOrgUnitDTOs.add(accountDTO);
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
        return (filterValue.equals("ro.teamnet.ou.web.rest.dto.AccountDTO"));
    }

}
