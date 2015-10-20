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
    Set<Function> findFunctionsByOrganizationalUnitId(@Param("organizationalUnitId") Long organizationalUnit);

    void deleteByOrganizationalUnitIdAndFunctionId(Long organizationalUnitId, Long functionId);

    @Query("select ouFunction from OrganizationalUnitFunction ouFunction where ouFunction.organizationalUnit.id =:ouId and ouFunction.function.id =:functionId")
    Set<OrganizationalUnitFunction> getByOrgUnitIdAndFunctionId(@Param("ouId") Long ouId, @Param("functionId") Long functionId);
}
