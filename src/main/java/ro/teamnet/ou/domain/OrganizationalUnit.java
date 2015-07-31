package ro.teamnet.ou.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;
import ro.teamnet.ou.domain.jpa.OrganizationalUnitFunction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@NodeEntity
@Entity
@Table(name = "T_ORGANIZATIONAL_UNIT")
public class OrganizationalUnit implements Serializable {

    @GraphId
    @Id
    @Column(name = "ID_ORGANIZATIONAL_UNIT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @GraphProperty(propertyName = "code", defaultValue = "")
    @NotNull
    @Column(name = "CODE", length = 100, unique = true)
    private String code;

    @Column(name = "DESCRIPTION")
    @GraphProperty(propertyName = "description", defaultValue = "")
    private String description;

    @NotNull
    @Column(name = "VALID_FROM")
    @GraphProperty(propertyName = "validFrom", defaultValue = "")
    @Temporal(TemporalType.DATE)
    protected Date validFrom;

    @NotNull
    @Column(name = "VALID_TO")
    @GraphProperty(propertyName = "validTo", defaultValue = "")
    @Temporal(TemporalType.DATE)
    protected Date validTo;

    @NotNull
    @Column(name = "IS_ACTIVE")
    @GraphProperty(propertyName = "active", defaultValue = "")
    protected Boolean active;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}