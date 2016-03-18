package ro.teamnet.ou.domain.jpa;


import ro.teamnet.bootstrap.domain.Account;

import javax.persistence.*;

/**
 * Created by Marian.Spoiala on 10/12/2015.
 */
@Entity
@Table(name = "T_ORGANIZATION_ACCOUNT")
public class OrganizationAccount {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID_ACCOUNT")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID")
    private Organization organization;

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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
