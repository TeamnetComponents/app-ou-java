package ro.teamnet.ou.domain.neo;

import org.springframework.data.neo4j.annotation.*;

import java.io.Serializable;
import java.util.Set;

@NodeEntity
public class Organization implements Serializable{
    @GraphId
    private Long id;

    @GraphProperty(propertyName = "code", defaultValue = "")
    private String code;

    @GraphProperty(propertyName = "jpaId", defaultValue = "")
    private Long jpaId;

    @RelatedToVia(type = "PERSPECTIVE")
    private Set<Perspective> perspectives;

    @RelatedTo(type = "PERSPECTIVE")
    private Set<OrganizationalUnit> roots;


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

    public Set<Perspective> getPerspectives() {
        return perspectives;
    }

    public void setPerspectives(Set<Perspective> perspectives) {
        this.perspectives = perspectives;
    }

    public Set<OrganizationalUnit> getRoots() {
        return roots;
    }

    public void setRoots(Set<OrganizationalUnit> roots) {
        this.roots = roots;
    }
}