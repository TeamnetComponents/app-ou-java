package ro.teamnet.ou.domain.jpa;

import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.domain.Function;

import java.util.Set;

public class AccountFunction {

    private Long id;
    private Account account;
    private Function function;
    private Set<OrganizationalUnitFunction> organizationalUnitFunctions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Set<OrganizationalUnitFunction> getOrganizationalUnitFunctions() {
        return organizationalUnitFunctions;
    }

    public void setOrganizationalUnitFunctions(Set<OrganizationalUnitFunction> organizationalUnitFunctions) {
        this.organizationalUnitFunctions = organizationalUnitFunctions;
    }
}
