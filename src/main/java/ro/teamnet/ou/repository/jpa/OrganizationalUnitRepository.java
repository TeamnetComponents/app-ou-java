package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;

import java.util.List;
import java.util.Set;

/**
 * Created by Marian.Spoiala on 7/31/2015.
 */
public interface OrganizationalUnitRepository extends AppRepository<OrganizationalUnit, Long> {
    @Query("select ou from OrganizationalUnit ou left join fetch ou.accountFunctions where ou.id = :id")
    OrganizationalUnit getOneWithAccountFunctions(@Param("id") Long id);

    @Query("select ou from OrganizationalUnit ou left join fetch ou.accountFunctions")
    Set<OrganizationalUnit> getAllWithAccountFunctions();

    @Query("select ou from OrganizationalUnit ou join ou.accountFunctions p where p.id = :id")
    List<OrganizationalUnit> getOrganizationalUnitByAccountFunctionId(@Param("id") Long id);

    @Query("select ou from OrganizationalUnit ou where ou.code = :code")
    List<OrganizationalUnit> getOrganizationalUnitByCode(@Param("code") String code);
}
