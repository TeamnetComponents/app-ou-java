package ro.teamnet.ou.domain.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by ionut.patrascu on 04.08.2015.
 */
@Entity
@Table(name="T_ORGANIZATIONAL_UNIT")
public class OrganizationalUnit implements Serializable{

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="CODE", length = 100, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "PARENT")
    private OrganizationalUnit parent;

    @OneToMany(mappedBy = "parent")
    private Set<OrganizationalUnit> children;

    @ManyToOne
    @JoinColumn(name = "PERSPECTIVE_ID", referencedColumnName = "ID")
    private Perspective perspective;

    public Perspective getPerspective() {
        return perspective;
    }

    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
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

    public OrganizationalUnit getParent() {
        return parent;
    }

    public void setParent(OrganizationalUnit parent) {
        this.parent = parent;
    }

    public Set<OrganizationalUnit> getChildren() {
        return children;
    }

    public void setChildren(Set<OrganizationalUnit> children) {
        this.children = children;
    }
}
