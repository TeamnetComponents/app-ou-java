package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the AccountFunction entity.
 */
public interface AccountFunctionRepository extends AppRepository<AccountFunction, Long> {

    @Query("select af.function from AccountFunction af where af.account.id = :accountId")
    Set<Function> findFunctionsByAccountId(@Param("accountId") Long accountId);

    @Query("select af from AccountFunction af where af.function.id = :functionId")
    List<AccountFunction> findAccountFunctionByFunctionId(@Param("functionId") Long functionId);

    @Query("select af from AccountFunction af left join fetch af.account left join fetch af.function where af.function in(:fcts)")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Set<AccountFunction> findByFunctionIn(@Param("fcts") Collection<Function> functions);

    Set<AccountFunction> findByAccountId(Long accountId);

    AccountFunction findByAccountIdAndFunctionId(Long accountId, Long functionId);

    void deleteByAccountIdAndFunctionId(Long accountId, Long functionId);
}
