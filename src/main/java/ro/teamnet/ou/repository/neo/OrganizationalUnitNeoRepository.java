package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.Organization;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationalUnitNeoRepository extends GraphRepository<Organization>, SchemaIndexRepository<Organization> {
}
