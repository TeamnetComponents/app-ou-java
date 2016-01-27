package ro.teamnet.ou.web.rest.dto;

import ro.teamnet.bootstrap.web.rest.dto.AccountDTO;
import ro.teamnet.ou.domain.jpa.Organization;

import java.util.Set;

public class AccountOrganizationsDTO {

    private AccountDTO account;
    private Set<Organization> organizations;

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }
}
