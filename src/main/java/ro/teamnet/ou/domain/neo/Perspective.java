package ro.teamnet.ou.domain.neo;

import org.springframework.data.neo4j.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type = "PERSPECTIVE")
public class Perspective implements Serializable {
    @GraphId
    private Long id;

    @RelationshipType
    private String code;

    @GraphProperty(propertyName = "jpaId", defaultValue = "")
    private Long jpaId;

    @StartNode
    private Organization organization;

    @EndNode
    private OrganizationalUnit organizationalUnit;

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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }
}
