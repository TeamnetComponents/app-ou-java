package ro.teamnet.ou.repository.neo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.Function;

import java.util.List;
import java.util.Set;


public interface FunctionNeoRepository extends GraphRepository<Function>, SchemaIndexRepository<Function> {
    @Query("MATCH (function:Function) RETURN function")
    Set<Function> getAllFunctions();

    @Query("MATCH (function:Function {jpaId:{0}}) RETURN function")
    Function findByJpaId(Long jpaId);

}
