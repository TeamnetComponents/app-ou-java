package ro.teamnet.ou.domain.neo;

import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity(type = "FUNCTION")
public class Function {

    @GraphId
    private Long id;

    @StartNode
    private Account account;

    @EndNode
    private OrganizationalUnit organizationalUnit;

    @GraphProperty(propertyName = "code")
    private String code;

    @GraphProperty(propertyName = "jpaId")
    private Long jpaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }
}
