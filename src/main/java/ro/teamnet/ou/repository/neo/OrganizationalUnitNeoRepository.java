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

    @Query("start q=node({0}) return DISTINCT q UNION ALL " +
            "start n=node({0}),x=node({1}) match(n:OrganizationalUnit)-[*]-(p:OrganizationalUnit),(q:OrganizationalUnit)-[*]->(n) " +
            "where ID(q)<>{1} and not(q)-->(x)   return DISTINCT q")
    Set<OrganizationalUnit> getParentOrgUnitsById(Long rootId, Long id);

    @Query("START a=node({0}), b=node({0})" +
            "MATCH (b:OrganizationalUnit)-[t]-(), (a:OrganizationalUnit)<-[r*1..]-(z:OrganizationalUnit)" +
            "FOREACH(rel IN r | DELETE rel)" +
            "DELETE b,t,z")
    void deleteNodeAndChildren(Long id);

    @Query("MATCH " +
            " (account:Account{jpaId:{0}})-[:FUNCTION]->(ou:OrganizationalUnit)-[:BELONGS_TO|:PERSPECTIVE*]-(o:Organization {jpaId:{1}})" +
            "RETURN ou")
    Set<OrganizationalUnit> getOrganizationalUnitsByAccountJpaIdAndOrganizationJpaId(Long accountJpaId, Long organizationJpaId);

    @Query("MATCH " +
            " (account:Account{username:{0}})-[:FUNCTION]->(ou:OrganizationalUnit)-[:BELONGS_TO|:PERSPECTIVE*]-(o:Organization {jpaId:{1}})" +
            "RETURN ou")
    Set<OrganizationalUnit> getOrganizationalUnitsByUsernameAndOrganization(String username, Long organizationJpaId);

}
