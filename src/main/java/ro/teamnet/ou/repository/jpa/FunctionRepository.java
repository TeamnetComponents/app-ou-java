package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.Function;

import java.util.Set;

/**
 * Spring Data JPA repository for the Role entity.
 */
public interface FunctionRepository extends AppRepository<Function, Long> {

    public Function findByCode(String code);

    @Query("select r from Function r left join fetch r.moduleRights where r.id =:id")
    Function getOneById(@Param("id") Long id);

    @Query("select r from Function r left join fetch r.moduleRights")
    Set<Function> getAllWithModuleRights();
}

