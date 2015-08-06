package ro.teamnet.ou.domain.jpa;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

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

    @OneToMany(mappedBy = "organization")
    private Set<Perspective> perspectives;

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
}
