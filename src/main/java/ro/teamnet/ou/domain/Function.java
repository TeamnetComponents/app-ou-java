package ro.teamnet.ou.domain;


import org.springframework.data.neo4j.annotation.*;
import ro.teamnet.bootstrap.domain.RoleBase;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RelationshipEntity
@Entity
@DiscriminatorValue("FUNCTION")
public class Function extends RoleBase{

    @StartNode
    private Account account;
    @EndNode
    private ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit;

    @GraphId
    @Id
    @Column(name = "ID_ROLE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @GraphProperty(propertyName = "code", defaultValue = "")
    @NotNull
    @Column(name = "CODE", length = 100, unique = true)
    private String code;

    @Column(name = "DESCRIPTION")
    @GraphProperty(propertyName = "description", defaultValue = "")
    protected String description;

    @GraphProperty(propertyName = "jpaId", defaultValue = "")
    private Long jpaId;

    @NotNull
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    @GraphProperty(propertyName = "validFrom", defaultValue = "")
    protected Date validFrom;

    @NotNull
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    @GraphProperty(propertyName = "validTo", defaultValue = "")
    protected Date validTo;

    @NotNull @Column(name = "IS_ACTIVE")
    @GraphProperty(propertyName = "active", defaultValue = "")
    protected Boolean active;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
