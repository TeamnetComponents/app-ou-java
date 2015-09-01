package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;

/**
 * Created by Marian.Spoiala on 7/31/2015.
 */
public interface OrganizationalUnitRepository extends AppRepository<OrganizationalUnit, Long> {
    @Query("select ou from OrganizationalUnit ou left join fetch ou.accountFunctions where ou.id = :id")
    OrganizationalUnit getOneWithAccountFunctions(@Param("id")Long id);
}
