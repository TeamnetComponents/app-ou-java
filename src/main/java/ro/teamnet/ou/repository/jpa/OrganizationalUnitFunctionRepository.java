package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.domain.jpa.OrganizationalUnitFunction;

import java.util.Set;

/**
 * Spring Data JPA repository for the OrganizationalUnitFunction entity.
 */
public interface OrganizationalUnitFunctionRepository extends AppRepository<OrganizationalUnitFunction, Long> {

    @Query("select ouFunction.function from OrganizationalUnitFunction ouFunction where ouFunction.organizationalUnit.id = :organizationalUnitId")
    Set<Function> findFunctionsByorganizationalUnitId(@Param("organizationalUnitId") Long organizationalUnit);

    AccountFunction findByOrganizationalUnitIdAndFunctionId(Long organizationalUnitId, Long functionId);

    void deleteByOrganizationalUnitIdAndFunctionId(Long organizationalUnitId, Long functionId);
}