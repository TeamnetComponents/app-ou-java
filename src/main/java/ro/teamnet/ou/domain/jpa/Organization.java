package ro.teamnet.ou.domain.jpa;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ionut.patrascu on 04.08.2015.
 */
@Entity
@Table(name="T_ORGANIZATION")
public class Organization implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "CODE", length = 100, unique = true)
    private String code;

    @Column(name = "DESCRIPTION")
    protected String description;

    @NotNull
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    protected Date validFrom;

    @NotNull
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    protected Date validTo;

    @NotNull @Column(name = "IS_ACTIVE")
    protected Boolean active;

    @OneToMany(mappedBy = "organization", orphanRemoval = true)
    private Set<Perspective> perspectives = new TreeSet<>();

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

    public Set<Perspective> getPerspectives() {
        return perspectives;
    }

    public void setPerspectives(Set<Perspective> perspectives) {
        this.perspectives = perspectives;
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
