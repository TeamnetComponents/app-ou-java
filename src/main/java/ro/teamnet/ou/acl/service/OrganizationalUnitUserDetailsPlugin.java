package ro.teamnet.ou.acl.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorPlugin;
import ro.teamnet.bootstrap.plugin.security.UserDetailsDecoratorType;
import ro.teamnet.bootstrap.plugin.security.UserDetailsExtension;
import ro.teamnet.bootstrap.security.CustomWebAuthenticationDetails;
import ro.teamnet.bootstrap.security.UserExtension;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.acl.domain.UserOrganizationalUnitDetails;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.repository.jpa.FunctionRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.service.OUAccountService;
import ro.teamnet.ou.service.OrganizationService;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.*;

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
    private AccountService accountService;

    @Inject
    private OUAccountService ouAccountService;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private OrganizationalUnitRepository ouRepository;

    @Inject
    private FunctionRepository functionRepository;

    @Override
    public UserDetailsExtension extendUserDetails(UserDetailsExtension user, Object authenticationDetails) {
        Account account = accountService.findByLogin(user.getUsername());
        OrganizationDTO authorizedOrganization = getAuthorizedOrganization(authenticationDetails);
        List<OrganizationalUnitDTO> authorizedOUs = getAuthorizedOUs(account.getId(), authorizedOrganization);

        HashMap<String, Object> extensions = new HashMap<>();
        extensions.put(USER_ORGANIZATIONAL_UNIT_DETAILS, new UserOrganizationalUnitDetails(authorizedOrganization, authorizedOUs));
        return new UserExtension(user, getGrantedAuthorities(user, account.getId(), authorizedOUs), extensions);
    }

    private OrganizationDTO getAuthorizedOrganization(Object authenticationDetails) {
        OrganizationDTO authorizedOrganization = null;
        if (authenticationDetails instanceof CustomWebAuthenticationDetails) {
            String extraDetails = ((CustomWebAuthenticationDetails) authenticationDetails).getExtraDetails();
            Long organizationId = getOrganizationId(extraDetails);
            if (organizationId != null) {
                authorizedOrganization = organizationService.findOrganizationDTOById(organizationId);
            }
        }
        return authorizedOrganization;
    }

    private Long getOrganizationId(String extraDetails) {
        try {
            return Long.valueOf(extraDetails);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    private List<OrganizationalUnitDTO> getAuthorizedOUs(Long accountId, OrganizationDTO authorizedOrganization) {
        List<OrganizationalUnitDTO> organizationalUnits = ouAccountService.getOrganizationalUnits(accountId);
        if (authorizedOrganization == null) {
            return organizationalUnits;
        }
        List<OrganizationalUnitDTO> authorizedOUs = new ArrayList<>();
        for (OrganizationalUnitDTO organizationalUnit : organizationalUnits) {
            for (OrganizationalUnit ouInAuthorizedOrg : organizationService.getOUsInOrganization(authorizedOrganization.getId())) {
                if (organizationalUnit.getId().equals(ouInAuthorizedOrg.getJpaId())) {
                    authorizedOUs.add(organizationalUnit);
                    break;
                }
            }
        }
        return authorizedOUs;
    }

    private Set<GrantedAuthority> getGrantedAuthorities(UserDetails userDetails, Long accountId,
                                                        List<OrganizationalUnitDTO> authorizedOUs) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(userDetails.getAuthorities());

        Set<Function> ouFunctions = new HashSet<>();
        for (OrganizationalUnitDTO authorizedOU : authorizedOUs) {
            for (AccountFunction accountFunction : ouRepository.getOneWithAccountFunctions(authorizedOU.getId()).getAccountFunctions()) {
                if (accountFunction.getAccount().getId().equals(accountId)) {
                    ouFunctions.add(functionRepository.getOneById(accountFunction.getFunction().getId()));
                }
            }
        }
        authorities.addAll(ouFunctions);
        return authorities;
    }

    @Override
    public boolean supports(UserDetailsDecoratorType userDetailsDecoratorType) {
        return userDetailsDecoratorType == UserDetailsDecoratorType.DEFAULT;
    }
}
