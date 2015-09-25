package ro.teamnet.ou.acl.service;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.security.util.SecurityUtils;
import ro.teamnet.ou.acl.domain.OrganizationalUnitUserDetails;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static ro.teamnet.ou.acl.domain.OrganizationalUnitHierarchyEntity.*;

@Service
public class OrganizationalUnitHierarchyFilterAdviceImpl implements OrganizationalUnitHierarchyFilterAdvice {

    @PersistenceContext(name = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    private OrganizationalUnitNeoRepository ouNeoRepository;

    public void setupOrganizationalUnitHierarchyFilter() {
        Session session = getSession();
        setupOrganizationalUnitHierarchyFilter(session);
    }

    public void setupOrganizationalUnitHierarchyFilter(Session session) {
        if (session == null) {
            return;
        }
        //Workaround to create user
        User authenticatedUser = SecurityUtils.getAuthenticatedUser();
        if (authenticatedUser == null) {
            return;
        }
        List<Long> authenticatedUserOUIds = getAuthenticatedUserOUIds();
        List<Long> ouHierarchy = getOUHierarchyForRoots(authenticatedUserOUIds);
        if (!ouHierarchy.isEmpty()) {
            Filter filter = session.getEnabledFilter(FILTER_BY_OWNER_ORGANIZATIONAL_UNITS);
            if (filter == null) {
                filter = session.enableFilter(FILTER_BY_OWNER_ORGANIZATIONAL_UNITS);
            }
            filter.setParameterList(OWNER_ORGANIZATIONAL_UNIT_IDS, ouHierarchy);
        } else {
            Filter filter = session.getEnabledFilter(FILTER_ALL_WHEN_NO_ORGANIZATIONAL_UNITS_ON_ACCOUNT);
            if (filter == null) {
                session.enableFilter(FILTER_ALL_WHEN_NO_ORGANIZATIONAL_UNITS_ON_ACCOUNT);
            }
        }
    }

    private Session getSession() {
        if (!(entityManager instanceof HibernateEntityManager)) {
            return null;
        }
        Session session = ((HibernateEntityManager) entityManager).getSession();
        if (!session.isOpen() || !session.getSessionFactory().getDefinedFilterNames().contains(FILTER_BY_OWNER_ORGANIZATIONAL_UNITS)) {
            return null;
        }
        return session;
    }

    public List<Long> getAuthenticatedUserOUIds() {
        User authenticatedUser = SecurityUtils.getAuthenticatedUser();
        if (authenticatedUser instanceof OrganizationalUnitUserDetails) {
            return ((OrganizationalUnitUserDetails) authenticatedUser).getOrganizationalUnitIds();
        }
        return new ArrayList<>();
    }

    private List<Long> getOUHierarchyForRoots(List<Long> rootIds) {
        List<Long> ouHierarchy = new ArrayList<>();
        for (Long rootId : rootIds) {
            ouHierarchy.add(rootId);
            ouHierarchy.addAll(ouNeoRepository.getOrganizationalUnitSubTreeJpaIdsByRootJpaId(rootId));
        }
        return ouHierarchy;
    }
}
