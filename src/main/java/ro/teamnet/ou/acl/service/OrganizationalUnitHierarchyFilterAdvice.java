package ro.teamnet.ou.acl.service;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.stereotype.Service;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static ro.teamnet.ou.acl.domain.OrganizationalUnitHierarchyEntity.FILTER_BY_OWNER_ORGANIZATIONAL_UNITS;
import static ro.teamnet.ou.acl.domain.OrganizationalUnitHierarchyEntity.OWNER_ORGANIZATIONAL_UNIT_IDS;

@Service
public class OrganizationalUnitHierarchyFilterAdvice {

    @PersistenceContext(name = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    private OrganizationalUnitNeoRepository ouNeoRepository;

    public void setupOrganizationalUnitHierarchyFilter(List<Long> organizationalUnitIds) {
        if (!(entityManager instanceof HibernateEntityManager)) {
            return;
        }
        Session session = ((HibernateEntityManager) entityManager).getSession();
        if (!session.getSessionFactory().getDefinedFilterNames().contains(FILTER_BY_OWNER_ORGANIZATIONAL_UNITS)) {
            return;
        }
        Filter filter = session.enableFilter(FILTER_BY_OWNER_ORGANIZATIONAL_UNITS);
        List<Long> ouHierarchy = getOUHierarchyForRoots(organizationalUnitIds);
        filter.setParameterList(OWNER_ORGANIZATIONAL_UNIT_IDS, ouHierarchy);

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
