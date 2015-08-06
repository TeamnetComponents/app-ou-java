package ro.teamnet.ou.domain.neo;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

@NodeEntity
public class Account {
    @GraphId
    private Long id;

    private Long jpaId;

    private String firstName;

    private String lastName;

    @Fetch
    @RelatedTo(type = "FUNCTION", direction = Direction.OUTGOING)
    private Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnits;

    @Fetch
    @RelatedToVia(type = "FUNCTION")
    private Set<Function> functions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> getOrganizationalUnits() {
        return organizationalUnits;
    }

    public void setOrganizationalUnits(Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnits) {
        this.organizationalUnits = organizationalUnits;
    }

    public Set<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }
}
