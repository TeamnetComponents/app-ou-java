package ro.teamnet.ou.acl.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.plugin.security.SecurityType;
import ro.teamnet.bootstrap.plugin.security.UserDetailsPlugin;
import ro.teamnet.bootstrap.security.DefaultUserDetailsPlugin;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.acl.domain.OrganizationalUnitUserDetails;
import ro.teamnet.ou.service.OUAccountService;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.List;

/**
 * An {@link UserDetailsPlugin} implementation that saves information about the organizational units associated with
 * the authenticated account.
 * @see OrganizationalUnitUserDetails
 */

@Service
public class OrganizationalUnitUserDetailsPlugin extends DefaultUserDetailsPlugin {

    @Inject
    private AccountService accountService;

    @Inject
    private OUAccountService ouAccountService;

    @Override
    public boolean supports(SecurityType delimiter) {
        return delimiter==SecurityType.USER_DETAILS;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserDetails(UserDetails userDetails) {
        Account account = accountService.findByLogin(userDetails.getUsername());
        List<Long> ouIds = ouAccountService.getOrganizationalUnitIds(account.getId());
        List<OrganizationalUnitDTO> organizationalUnits = ouAccountService.getOrganizationalUnits(account.getId());
        return new OrganizationalUnitUserDetails(super.loadUserDetails(userDetails), ouIds, organizationalUnits);
    }

}
