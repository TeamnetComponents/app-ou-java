package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.Perspective;

import java.util.Set;

/**
 * Created by Marian.Spoiala on 7/31/2015.
 */
public interface PerspectiveRepository extends AppRepository<Perspective, Long> {

    @Query("select p from Perspective p left join fetch p.organization where p.organization.id = :id")
    Set<Perspective> findByOrganizationId(@Param("id")Long id);

}
