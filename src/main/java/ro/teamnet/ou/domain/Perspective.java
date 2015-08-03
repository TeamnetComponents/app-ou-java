package ro.teamnet.ou.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.neo4j.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RelationshipEntity
@Entity
@Table(name = "T_PERSPECTIVE")

public class Perspective {
    @GraphId
    @Id
    @Column(name = "ID_PERSPECTIVE")
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

    @ManyToOne
    @StartNode
    private Organization organization;

    @EndNode
    private OrganizationalUnit organizationalUnit;

    @OneToMany(mappedBy = "perspective")
    @JsonManagedReference
    private Set<OrganizationalUnit> organizationalUnits;
}
