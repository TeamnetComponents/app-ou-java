package ro.teamnet.ou.domain.jpa;

import ro.teamnet.bootstrap.domain.Account;

import java.util.Set;

public class OrganizationalUnit {

    private Long id;


    private Set<Account> accounts;
    private Set<Function> functions;

    private OrganizationalUnit parent;

    private Set<OrganizationalUnit> children;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}