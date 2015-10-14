package ro.teamnet.ou.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.jpa.OrganizationAccount;

import java.util.Set;

/**
 * Created by Marian.Spoiala on 10/12/2015.
 */
@Repository
public interface OrganizationAccountRepository extends AppRepository<OrganizationAccount, Long> {

    @Query("SELECT orgAccount.account FROM OrganizationAccount orgAccount WHERE orgAccount.organization.id=:orgId")
    Set<Account> getAccountsByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT orgAccount FROM OrganizationAccount orgAccount WHERE orgAccount.organization.id=:orgId")
    Set<OrganizationAccount> findByOrganizationId(@Param("orgId") Long orgId);

    @Query("SELECT orgAccount.organization FROM OrganizationAccount orgAccount WHERE orgAccount.account.id=:accountId")
    Set<Organization> getOrgsByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT orgAccount.organization FROM OrganizationAccount orgAccount WHERE orgAccount.account.login=:accountUsername")
    Set<Organization> getOrgsByAccountUsername(@Param("accountUsername") String accountUsername);
}
