package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;

import java.util.Set;

/**
 * Spring Data JPA repository for the AccountFunction entity.
 */
public interface AccountFunctionRepository extends AppRepository<AccountFunction, Long> {

    @Query("select af.function from AccountFunction af where af.account.id = :accountId")
    Set<Function> findFunctionsByAccountId(@Param("accountId")Long accountId);

    AccountFunction findByAccountIdAndFunctionId(Long accountId, Long functionId);
    void deleteByAccountIdAndFunctionId(Long accountId, Long functionId);
}
