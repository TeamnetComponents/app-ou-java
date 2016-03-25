package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.Account;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface AccountNeoRepository extends GraphRepository<Account>, SchemaIndexRepository<Account> {
    @Query("MATCH (account:Account{jpaId:{0}}) RETURN account")
    Account findByJpaId(Long jpaId);
}
