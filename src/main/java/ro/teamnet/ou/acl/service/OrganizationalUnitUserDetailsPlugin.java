package ro.teamnet.ou.acl.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorPlugin;
import ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorType;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.acl.domain.OrganizationalUnitUserDetails;
import ro.teamnet.ou.service.OUAccountService;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.List;

/**
 * An {@link ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorPlugin} implementation that saves information about the organizational units associated with
 * the authenticated account.
 * @see OrganizationalUnitUserDetails
 */

@Service
public class OrganizationalUnitUserDetailsPlugin implements UserDetailsDecoratorPlugin {

    @Inject
    private AccountService accountService;

    @Inject
    private OUAccountService ouAccountService;

    @Override
    public UserDetails extendUserDetails(UserDetails userDetails) {
        Account account = accountService.findByLogin(userDetails.getUsername());
        List<Long> ouIds = ouAccountService.getOrganizationalUnitIds(account.getId());
        List<OrganizationalUnitDTO> organizationalUnits = ouAccountService.getOrganizationalUnits(account.getId());
        return new OrganizationalUnitUserDetails(userDetails, ouIds, organizationalUnits);
    }

    @Override
    public boolean supports(UserDetailsDecoratorType userDetailsDecoratorType) {
        return userDetailsDecoratorType==UserDetailsDecoratorType.DEFAULT;
    }
}
