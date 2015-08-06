package ro.teamnet.ou.domain.jpa;

import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.domain.Function;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="T_ACCOUNT_FUNCTION")
public class AccountFunction {

    @Id
    @Column(name="ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID", referencedColumnName = "ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name="FUNCTION_ID", referencedColumnName = "ID")
    private Function function;


    //private Set<OrganizationalUnitFunction> organizationalUnitFunctions;


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
}
