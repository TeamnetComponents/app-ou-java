package ro.teamnet.ou.domain.jpa;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by ionut.patrascu on 04.08.2015.
 */
@Entity
@Table(name="T_PERSPECTIVE")
public class Perspective implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "CODE", length = 100, unique = true)
    private String code;

    @Column(name = "DESCRIPTION")
    protected String description;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID")
    private Organization organization;


    @OneToMany(mappedBy = "perspective")
    @JsonManagedReference
    private Set<OrganizationalUnit> organizationalUnits;

}
