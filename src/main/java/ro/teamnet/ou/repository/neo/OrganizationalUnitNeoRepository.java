package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;

import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationalUnitNeoRepository extends GraphRepository<OrganizationalUnit>, SchemaIndexRepository<OrganizationalUnit> {

    @Query("start n=node({0}) match (n:OrganizationalUnit) return n " +
            "UNION ALL start rootOu=node({0}) " +
                        "match (n:OrganizationalUnit)-[:BELONGS_TO*]->(rootOu:OrganizationalUnit) return n")
    Set<OrganizationalUnit> getOrganizationalUnitTreeById(Long id);

    @Query("match (childOu:OrganizationalUnit)-[:BELONGS_TO*]->(rootOu:OrganizationalUnit{jpaId : {0}}) return childOu.jpaId")
    Set<Long> getOrganizationalUnitSubTreeJpaIdsByRootJpaId(Long jpaId);

    @Query("MATCH (organizationalUnit:OrganizationalUnit) RETURN organizationalUnit")
    Set<OrganizationalUnit> getAllOrganizationalUnits();
    
    @Query("MATCH (organizationalUnit:OrganizationalUnit {jpaId:{0}}) RETURN organizationalUnit")
    OrganizationalUnit findByJpaId(Long jpaId);

    @Query("start n=node({0}),x=node({1}) match(n:OrganizationalUnit)-[*]-(p:OrganizationalUnit),(q:OrganizationalUnit)-[*]->(n) where ID(q)<>{1} and not(q)-->(x)   return q")
    Set<OrganizationalUnit> getParentOrgUnitsById(Long rootId, Long id);
}
