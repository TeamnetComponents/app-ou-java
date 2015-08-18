package ro.teamnet.ou.acl.service;

import org.hibernate.Session;

public interface OrganizationalUnitHierarchyFilterAdvice {
    void setupOrganizationalUnitHierarchyFilter();
    void setupOrganizationalUnitHierarchyFilter(Session session);
}
