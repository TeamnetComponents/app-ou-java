package ro.teamnet.ou.domain;

import org.springframework.data.neo4j.annotation.*;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@NodeEntity
@Entity
@Table(name = "T_ORGANIZATION")
public class Organization implements Serializable{
    @GraphId
    @Id
    @Column(name = "ID_ORGANIZATION")
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

    @RelatedToVia(type = "PERSPECTIVE")
    @OneToMany(mappedBy = "organization")
    private Set<ro.teamnet.ou.domain.Perspective> perspectives;

    @RelatedTo(type = "PERSPECTIVE")
    private Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> roots;


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