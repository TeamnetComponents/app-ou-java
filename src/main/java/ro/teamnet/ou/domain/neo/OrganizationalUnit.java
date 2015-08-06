package ro.teamnet.ou.domain.neo;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class OrganizationalUnit {

    @GraphId
    private Long id;

    private Long jpaId;

    private String code;

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

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OrganizationalUnit getParent() {
        return parent;
    }

    public void setParent(OrganizationalUnit parent) {
        this.parent = parent;
    }

    public Set<OrganizationalUnit> getChildren() {
        return children;
    }

    public void setChildren(Set<OrganizationalUnit> children) {
        this.children = children;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
