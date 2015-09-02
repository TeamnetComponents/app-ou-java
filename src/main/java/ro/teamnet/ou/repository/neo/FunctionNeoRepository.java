package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.Function;

import java.util.Set;


public interface FunctionNeoRepository extends GraphRepository<Function>, SchemaIndexRepository<Function> {
    @Query("match (n:OrganizationalUnit)<-[r:FUNCTION]-(p:Account) where r.jpaId={0} return r")
    Set<Function> findByJpaId(Long jpaId);

    @Query("match (n:OrganizationalUnit)<-[r:FUNCTION]-(p:Account) where n.jpaId={0} return r")
    Set<Function> findByOrganizationalUnitJpaId(Long ouId);

    @Query("match (n:OrganizationalUnit)<-[r:FUNCTION]-(p:Account) where n.jpaId={0} delete r")
    void deleteByOrganizationalUnitJpaId(Long ouId);

    @Query("match (n:OrganizationalUnit)<-[r:FUNCTION]-(p:Account) where n.jpaId={0} and p.jpaId={1} delete r")
    void deleteByOrganizationalUnitJpaIdAndAccountJpaId(Long ouId, Long accountId);
}
