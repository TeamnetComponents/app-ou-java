package ro.teamnet.ou.domain.jpa;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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


    @OneToOne(mappedBy = "perspective", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private OrganizationalUnit ouTreeRoot;

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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public OrganizationalUnit getOuTreeRoot() {
        return ouTreeRoot;
    }

    public void setOuTreeRoot(OrganizationalUnit ouTreeRoot) {
        this.ouTreeRoot = ouTreeRoot;
    }
}
