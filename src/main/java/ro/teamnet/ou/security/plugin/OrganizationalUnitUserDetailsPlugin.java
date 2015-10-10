package ro.teamnet.ou.security.plugin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorPlugin;
import ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorType;
import ro.teamnet.bootstrap.plugin.security.UserDetailsExtension;
import ro.teamnet.bootstrap.security.CustomWebAuthenticationDetails;
import ro.teamnet.bootstrap.security.UserExtension;
import ro.teamnet.ou.security.UserOrganizationalUnitDetails;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.service.OrganizationalUnitLoginService;
import ro.teamnet.ou.service.OrganizationalUnitService;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;

/**
 * An {@link ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorPlugin} implementation that saves information
 * about the organizational units associated with the authenticated account.
 *
 * @see UserOrganizationalUnitDetails
 */

@Service
@Transactional(readOnly = true)
public class OrganizationalUnitUserDetailsPlugin implements UserDetailsDecoratorPlugin {

    public static final String USER_ORGANIZATIONAL_UNIT_DETAILS = "UserOrganizationalUnitDetails";
    @Inject
    private OrganizationService organizationService;

    @Inject
    private OrganizationalUnitService ouService;

    @Inject
    private OrganizationalUnitLoginService ouLoginService;


    @Override
    public UserDetailsExtension extendUserDetails(UserDetailsExtension user, Object authenticationDetails) {
        OrganizationDTO authorizedOrganization = getAuthorizedOrganization(authenticationDetails);
        Collection<OrganizationalUnitDTO> authorizedOUs = authorizedOrganization == null ?
                ouService.getOUsForUser(user.getUsername())
                :
                ouService.getOUsForUser(user.getUsername(), authorizedOrganization.getId());

        UserOrganizationalUnitDetails organizationalUnitDetails = new UserOrganizationalUnitDetails(authorizedOrganization, authorizedOUs);
        ouLoginService.updateUserFunctions(user.getUsername(), organizationalUnitDetails);

        HashMap<String, Object> extensions = new HashMap<>();
        extensions.put(USER_ORGANIZATIONAL_UNIT_DETAILS, organizationalUnitDetails);

        return new UserExtension(user, extensions);
    }

    private OrganizationDTO getAuthorizedOrganization(Object authenticationDetails) {
        Long organizationId = getOrganizationId(authenticationDetails);
        if (organizationId != null) {
            return organizationService.findOrganizationDTOById(organizationId);
        }
        return null;
    }

    private Long getOrganizationId(Object authenticationDetails) {
        if (!(authenticationDetails instanceof CustomWebAuthenticationDetails)) {
            return null;
        }
        String extraDetails = ((CustomWebAuthenticationDetails) authenticationDetails).getExtraDetails();
        if (extraDetails == null) {
            return null;
        }
        try {
            return Long.valueOf(extraDetails);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    @Override
    public boolean supports(UserDetailsDecoratorType userDetailsDecoratorType) {
        return userDetailsDecoratorType == UserDetailsDecoratorType.DEFAULT;
    }
}
