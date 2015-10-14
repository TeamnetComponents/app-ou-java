package ro.teamnet.ou.acl.service;

import org.hibernate.Session;

import java.util.Collection;

public interface OrganizationalUnitHierarchyFilterAdvice {
    void setupOrganizationalUnitHierarchyFilter();

    void setupOrganizationalUnitHierarchyFilter(Session session);

    Collection<Long> getAuthenticatedUserOUIds();
}
