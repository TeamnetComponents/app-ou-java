package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.Function;

import java.util.Set;


public interface FunctionNeoRepository extends GraphRepository<Function>, SchemaIndexRepository<Function> {
    Set<Function> findByJpaId(Long jpaId);

    Set<Function> findByOrganizationalUnitJpaId(Long ouId);
}
