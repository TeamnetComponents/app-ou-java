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

    @Fetch
    @GraphProperty(propertyName = "code")
    private String code;

    @Fetch
    @GraphProperty(propertyName = "jpaId")
    private Long jpaId;

    public Function() {
    }

    ;

    public Function(Long id, Account account, OrganizationalUnit organizationalUnit, String code, Long jpaId) {
        this.id = id;
        this.account = account;
        this.organizationalUnit = organizationalUnit;
        this.code = code;
        this.jpaId = jpaId;
    }

    public Function(Function function) {
        this.id = function.getId();
        this.account = function.getAccount();
        this.organizationalUnit = function.getOrganizationalUnit();
        this.code = function.getCode();
        this.jpaId = function.getJpaId();
    }

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
