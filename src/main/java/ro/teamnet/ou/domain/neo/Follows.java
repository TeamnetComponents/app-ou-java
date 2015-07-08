package ro.teamnet.ou.domain.neo;


import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class Follows {

    @StartNode
    private Person person;


    @EndNode
    private Person follows;

    @GraphId
    private Long id;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getFollows() {
        return follows;
    }

    public void setFollows(Person follows) {
        this.follows = follows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
