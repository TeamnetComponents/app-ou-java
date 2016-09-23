package ro.teamnet.ou.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.plugin.security.UserDetailsExtension;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.repository.jpa.FunctionRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.security.UserOrganizationalUnitDetails;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static ro.teamnet.ou.security.plugin.OrganizationalUnitUserDetailsPlugin.USER_ORGANIZATIONAL_UNIT_DETAILS;

/**
 * Created by Oana.Mihai on 10/9/2015.
 */
@Service
@Transactional(value="jpaTransactionManager", readOnly = true)
public class OrganizationalUnitLoginServiceImpl implements OrganizationalUnitLoginService{
    @Inject
    private OrganizationService organizationService;
    @Inject
    private OrganizationalUnitService ouService;
    @Inject
    private OrganizationalUnitRepository ouRepository;
    @Inject
    private FunctionRepository functionRepository;

    @Override
    public UserOrganizationalUnitDetails getUserOrganizationalUnitDetails() {
        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();
        if (authenticatedUser == null || !(authenticatedUser instanceof UserDetailsExtension)) {
            return new UserOrganizationalUnitDetails(null, new HashSet<OrganizationalUnitDTO>());
        }
        UserOrganizationalUnitDetails userOrganizationalUnitDetails = (UserOrganizationalUnitDetails) ((UserDetailsExtension) authenticatedUser).getExtensions().get(USER_ORGANIZATIONAL_UNIT_DETAILS);
        if (userOrganizationalUnitDetails == null) {
            userOrganizationalUnitDetails = new UserOrganizationalUnitDetails(null, new HashSet<OrganizationalUnitDTO>());
            ((UserDetailsExtension) authenticatedUser).getExtensions().put(USER_ORGANIZATIONAL_UNIT_DETAILS, userOrganizationalUnitDetails);
        }
        return userOrganizationalUnitDetails;
    }

    @Override
    public UserOrganizationalUnitDetails authenticateInAllOrganizations() {
        UserOrganizationalUnitDetails userOuDetails = getUserOrganizationalUnitDetails();
        userOuDetails.setAuthorizedOrganization(null);
        userOuDetails.setAuthorizedOUs(ouService.getOUsForCurrentUser());
        updateCurrentUserFunctions(userOuDetails);
        return userOuDetails;
    }

    @Override
    public UserOrganizationalUnitDetails authenticateInOrganization(Long organizationId) {
        if (organizationId == null) {
            return authenticateInAllOrganizations();
        }
        UserOrganizationalUnitDetails userOuDetails = getUserOrganizationalUnitDetails();
        userOuDetails.setAuthorizedOrganization(organizationService.findOrganizationDTOById(organizationId));
        userOuDetails.setAuthorizedOUs(ouService.getOUsForCurrentUser(organizationId));
        updateCurrentUserFunctions(userOuDetails);
        return userOuDetails;
    }

    @Override
    public UserOrganizationalUnitDetails authenticateInOU(Long organizationId, Long ouId) {
        if (ouId == null) {
            return authenticateInOrganization(organizationId);
        }
        UserOrganizationalUnitDetails userOuDetails = getUserOrganizationalUnitDetails();
        userOuDetails.setAuthorizedOrganization(organizationService.findOrganizationDTOById(organizationId));
        HashSet<OrganizationalUnitDTO> authorizedOUs = new HashSet<>();
        authorizedOUs.add(ouService.findOne(ouId));
        userOuDetails.setAuthorizedOUs(authorizedOUs);
        updateCurrentUserFunctions(userOuDetails);
        return userOuDetails;
    }

    private void updateCurrentUserFunctions(UserOrganizationalUnitDetails userOuDetails) {
        updateUserFunctions(SecurityUtils.getCurrentLogin(), userOuDetails);
    }

    @Override
    public void updateUserFunctions(String username, UserOrganizationalUnitDetails userOuDetails) {
        Set<Function> ouFunctions = new HashSet<>();
        for (OrganizationalUnitDTO authorizedOU : userOuDetails.getAuthorizedOUs()) {
            for (AccountFunction accountFunction : getAccountFunctions(authorizedOU.getId())) {
                if (accountFunction.getAccount().getLogin().equals(username)) {
                    ouFunctions.add(functionRepository.getOneById(accountFunction.getFunction().getId()));
                }
            }
        }
        userOuDetails.setFunctions(ouFunctions);
    }

    private Set<AccountFunction> getAccountFunctions(Long authorizedOUId) {
        return ouRepository.getOneWithAccountFunctions(authorizedOUId).getAccountFunctions();
    }
}
