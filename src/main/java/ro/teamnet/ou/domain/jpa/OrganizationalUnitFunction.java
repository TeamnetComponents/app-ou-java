package ro.teamnet.ou.domain.jpa;

import javax.persistence.*;


@Entity
@Table(name="T_ORGANIZATIONAL_UNIT_FUNCTION")
public class OrganizationalUnitFunction {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ORGANIZATIONAL_UNIT_ID", referencedColumnName = "ID")
    private OrganizationalUnit organizationalUnit;

    @ManyToOne
    @JoinColumn(name="FUNCTION_ID", referencedColumnName = "ID_ROLE")
    private Function ouFunction;


    //private Set<AccountFunction> accountFunctions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Function getOuFunction() {
        return ouFunction;
    }

    public void setOuFunction(Function ouFunction) {
        this.ouFunction = ouFunction;
    }
}
