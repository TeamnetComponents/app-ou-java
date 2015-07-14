package ro.teamnet.ou.domain.jpa;

import ro.teamnet.ou.domain.Function;
import ro.teamnet.ou.domain.OrganizationalUnit;

import java.util.Set;

public class OrganizationalUnitFunction {

    private Long id;
    private OrganizationalUnit organizationalUnit;

    private Function ouFunction;
    private Set<AccountFunction> accountFunctions;

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

    public Set<AccountFunction> getAccountFunctions() {
        return accountFunctions;
    }

    public void setAccountFunctions(Set<AccountFunction> accountFunctions) {
        this.accountFunctions = accountFunctions;
    }
}
