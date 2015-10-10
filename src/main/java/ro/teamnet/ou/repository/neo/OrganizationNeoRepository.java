package ro.teamnet.ou.repository.neo;


import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.Organization;

import java.util.List;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationNeoRepository extends GraphRepository<Organization>, SchemaIndexRepository<Organization> {

    @Query("MATCH (organization:Organization) RETURN organization")
    List<Organization> getAllOrganizations();

    @Query("MATCH (organization:Organization {jpaId:{0}}) RETURN organization")
    Organization findByJpaId(Long jpaId);

    @Query("MATCH (account:Account{jpaId:{0}})-[*]->(rootOu:OrganizationalUnit)<-[:PERSPECTIVE]-(o:Organization) return o")
    List<Organization> findByAccountJpaId(Long accountId);

    @Query("MATCH (account:Account{username:{0}})-[*]->(rootOu:OrganizationalUnit)<-[:PERSPECTIVE]-(o:Organization) return o")
    List<Organization> findByUsername(String username);
}
