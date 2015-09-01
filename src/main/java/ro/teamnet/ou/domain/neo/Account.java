package ro.teamnet.ou.domain.neo;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

@NodeEntity
public class Account {
    @GraphId
    private Long id;

    @GraphProperty(propertyName = "jpaId")
    private Long jpaId;

    @GraphProperty(propertyName = "username")
    private String username;

    @Fetch
    @RelatedTo(type = "FUNCTION", direction = Direction.OUTGOING)
    private Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnits;

    @Fetch
    @RelatedToVia(type = "FUNCTION", direction = Direction.OUTGOING)
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<OrganizationalUnit> getOrganizationalUnits() {
        return organizationalUnits;
    }

    public void setOrganizationalUnits(Set<OrganizationalUnit> organizationalUnits) {
        this.organizationalUnits = organizationalUnits;
    }

    public Set<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }
}
