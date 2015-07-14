package ro.teamnet.ou.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import ro.teamnet.ou.domain.jpa.OrganizationalUnitFunction;

import javax.persistence.*;
import java.util.Set;

@NodeEntity
@Entity
@Table(name = "T_ORGANIZATIONAL_UNIT")
public class OrganizationalUnit{

    @GraphId
    @Id
    @Column(name = "ID_ORGANIZATIONAL_UNIT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    private Set<OrganizationalUnitFunction> organizationalUnitFunctions;

    @RelatedTo(type="BELONGS_TO",direction = Direction.OUTGOING)
    private OrganizationalUnit parent;

    @RelatedTo(type="BELONGS_TO",direction = Direction.INCOMING)
    private Set<OrganizationalUnit> children;

    @Fetch
    @RelatedTo(type = "FUNCTION", direction = Direction.INCOMING)
    private Set<Account> accounts;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}