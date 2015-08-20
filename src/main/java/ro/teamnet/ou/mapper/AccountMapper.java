package ro.teamnet.ou.mapper;


import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.web.rest.dto.AccountDTO;

/**
 * Created by Marian.Spoiala on 8/19/2015.
 */
public class AccountMapper {

    public static Account toJpa(AccountDTO accountDTO) {

        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setLastName(accountDTO.getLastName());
        account.setFirstName(accountDTO.getFirstName());
        account.setActivated(accountDTO.getActivated());
        account.setEmail(accountDTO.getEmail());
        account.setGender(accountDTO.getGender());
        account.setLogin(accountDTO.getLogin());
        account.setLangKey(accountDTO.getLangKey());
        /*account.setRoles(accountDTO.getRoles());
        account.setModuleRights(accountDTO.getModuleRights());*/

        return account;
    }

    public static ro.teamnet.ou.domain.neo.Account toNeo(AccountDTO accountDTO) {

        ro.teamnet.ou.domain.neo.Account account = new ro.teamnet.ou.domain.neo.Account();

        account.setJpaId(accountDTO.getId());
        account.setUsername(accountDTO.getLogin());
        account.setOrganizationalUnits(accountDTO.getOrganizationalUnits());
        account.setFunctions(accountDTO.getFunctions());

        return account;
    }

    public static AccountDTO toDTO(Account account) {

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId(account.getId());
        accountDTO.setLastName(account.getLastName());
        accountDTO.setFirstName(account.getFirstName());
        //accountDTO.setActivated(account.getActivated());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setGender(account.getGender());
        accountDTO.setLogin(account.getLogin());
        accountDTO.setLangKey(account.getLangKey());
        /*accountDTO.setRoles(account.getRoles());
        accountDTO.setModuleRights(account.getModuleRights());*/

        return accountDTO;
    }

    public static AccountDTO toDTO(ro.teamnet.ou.domain.neo.Account account) {

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId(account.getJpaId());
        accountDTO.setLogin(account.getUsername());
        accountDTO.setOrganizationalUnits(account.getOrganizationalUnits());
        accountDTO.setFunctions(account.getFunctions());

        return accountDTO;
    }

}