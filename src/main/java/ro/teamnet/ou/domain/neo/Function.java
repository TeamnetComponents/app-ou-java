package ro.teamnet.ou.domain.neo;

import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity
public class Function {

    @StartNode
    private Account account;
    @EndNode
    private OrganizationalUnit organizationalUnit;

    @GraphProperty(propertyName = "code", defaultValue = "")
    private String code;

    @GraphId
    @GraphProperty(propertyName = "jpaId", defaultValue = "")
    private Long jpaId;


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
