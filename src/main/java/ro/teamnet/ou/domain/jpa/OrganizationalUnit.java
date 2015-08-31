package ro.teamnet.ou.domain.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    protected Date validFrom;

    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    protected Date validTo;

    @NotNull
    @Column(name = "IS_ACTIVE")
    protected Boolean active;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    @JsonBackReference
    private OrganizationalUnit parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrganizationalUnit> children;

    @OneToOne
    @JoinColumn(name = "PERSPECTIVE_ID")
    private Perspective perspective;


    @ManyToMany//(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name = "T_OU_ACCOUNT_FUNCTION",
            joinColumns = {@JoinColumn(name = "account_function_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ou_id", referencedColumnName = "id")})
    private Set<AccountFunction> accountFunctions = new HashSet<>();

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

    public Perspective getPerspective() {
        return perspective;
    }

    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }

    public Set<AccountFunction> getAccountFunctions() {
        return accountFunctions;
    }

    public void setAccountFunctions(Set<AccountFunction> accountFunctions) {
        this.accountFunctions = accountFunctions;
    }
}
