package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.Perspective;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface PerspectiveNeoRepository extends GraphRepository<Perspective>, SchemaIndexRepository<Perspective> {
}
