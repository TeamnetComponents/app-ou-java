package ro.teamnet.ou.acl.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.userdetails.UserDetails;
import ro.teamnet.ou.acl.domain.OrganizationalUnitHierarchyEntity;
import ro.teamnet.ou.acl.domain.OrganizationalUnitUserDetails;
import ro.teamnet.ou.acl.service.OrganizationalUnitHierarchyFilterAdvice;
import ro.teamnet.ou.acl.service.OrganizationalUnitUserDetailsPlugin;

import javax.inject.Inject;

/**
 * Aspect that enables the filtering of entities extending {@link OrganizationalUnitHierarchyEntity} based on the
 * organizational units associated with the authenticated account.
 * @see OrganizationalUnitUserDetailsPlugin#loadUserDetails(UserDetails)
 */
@Aspect
public class OrganizationalUnitHierarchyFilterAspect {

    @Inject
    OrganizationalUnitHierarchyFilterAdvice filterAdvice;

    @Pointcut("execution(* ro.teamnet.ou.acl.service.OrganizationalUnitUserDetailsPlugin.loadUserDetails(..))")
    public void onLoadUserDetails() {
    }

    @AfterReturning(pointcut = "onLoadUserDetails()", returning = "userDetails")
    public void enableFilter(OrganizationalUnitUserDetails userDetails) {
        filterAdvice.setupOrganizationalUnitHierarchyFilter(userDetails.getOrganizationalUnitIds());
    }


}
