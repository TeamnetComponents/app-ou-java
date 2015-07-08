package ro.teamnet.ou.repository.neo;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import ro.teamnet.ou.domain.neo.Person;


public interface PersonMovieRepository extends GraphRepository<Person>, SchemaIndexRepository<Person> {


}
