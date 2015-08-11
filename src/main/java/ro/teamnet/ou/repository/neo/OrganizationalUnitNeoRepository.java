package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;

import java.util.List;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationalUnitNeoRepository extends GraphRepository<OrganizationalUnit>, SchemaIndexRepository<OrganizationalUnit> {

    @Query("start n=node({0}) return n UNION ALL start m=node({0}) match (m:OrganizationalUnit)<-[r*]-n return n")
    public List<OrganizationalUnit> getOrganizationalUnitTreeById(Long id);

    @Query("start n=node({0}) return n.jpaId UNION ALL start m=node({0}) match (m:OrganizationalUnit)<-[r*]-n return n.jpaId")
    public List<Long> getOrganizationalUnitTreeIdsById(Long id);
}
