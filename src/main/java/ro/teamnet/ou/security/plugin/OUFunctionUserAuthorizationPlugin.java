package ro.teamnet.ou.security.plugin;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.domain.util.ModuleRightTypeEnum;
import ro.teamnet.bootstrap.plugin.security.BaseUserAuthorizationPlugin;
import ro.teamnet.bootstrap.plugin.security.SecurityType;
import ro.teamnet.bootstrap.plugin.security.UserDetailsExtension;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.ou.security.UserOrganizationalUnitDetails;
import ro.teamnet.ou.domain.jpa.Function;

import static ro.teamnet.ou.security.plugin.OrganizationalUnitUserDetailsPlugin.USER_ORGANIZATIONAL_UNIT_DETAILS;

/**
 * Created by Oana.Mihai on 10/9/2015.
 */
@Service
@Order(10)
public class OUFunctionUserAuthorizationPlugin extends BaseUserAuthorizationPlugin {

    @Override
    public Boolean grantAccessToResource(String resource, ModuleRightTypeEnum accessLevel) {
        UserDetails authenticatedUser = SecurityUtils.getAuthenticatedUser();
        if (authenticatedUser == null || accessLevel == null || !(authenticatedUser instanceof UserDetailsExtension)) {
            return false;
        }
        UserOrganizationalUnitDetails userOuDetails = (UserOrganizationalUnitDetails) ((UserDetailsExtension) authenticatedUser).getExtensions().get(USER_ORGANIZATIONAL_UNIT_DETAILS);
        if (userOuDetails == null) {
            return false;
        }

        for (Function function : userOuDetails.getFunctions()) {
            for (ModuleRight moduleRight : function.getModuleRights()) {
                if (moduleRightMatchesRequest(moduleRight, resource, accessLevel)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean supports(SecurityType delimiter) {
        return delimiter == SecurityType.USER_AUTHORIZATION;
    }
}
