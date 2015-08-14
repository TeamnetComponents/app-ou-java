package ro.teamnet.ou.acl.service;

import org.springframework.stereotype.Service;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationalUnitHierarchyFilterAdvice {

//    @Inject
//    private SessionFactory sessionFactory;

    @Inject
    private OrganizationalUnitNeoRepository ouNeoRepository;

    public void setupOrganizationalUnitHierarchyFilter(List<Long> organizationalUnitIds) {
//        Session session = sessionFactory.getCurrentSession();
//        Filter filter = session.enableFilter("filterByOwnerOrganizationalUnits");
        List<Long> ouHierarchy = getOUHierarchyForRoots(organizationalUnitIds);
//        filter.setParameterList("ownerOrganizationalUnitIds", ouHierarchy);
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
