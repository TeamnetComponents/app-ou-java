package ro.teamnet.ou.domain.jpa;

import javax.persistence.*;


@Entity
@Table(name="T_OU_FUNCTION")
public class OrganizationalUnitFunction {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="OU_ID", referencedColumnName = "ID")
    private OrganizationalUnit organizationalUnit;

    @ManyToOne
    @JoinColumn(name="FUNCTION_ID", referencedColumnName = "ID_ROLE")
    private Function function;


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

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function ouFunction) {
        this.function = ouFunction;
    }
}
