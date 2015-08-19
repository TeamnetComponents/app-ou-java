package ro.teamnet.ou.mapper;

import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.web.rest.dto.AccountDTO;

/**
 * Created by Marian.Spoiala on 8/19/2015.
 */
public class AccountMapper {

    public static Account toJpa(AccountDTO accountDTO) {

        Account account = new Account();

        return account;
    }

    public static ro.teamnet.ou.domain.neo.Account toNeo(AccountDTO accountDTO) {

        ro.teamnet.ou.domain.neo.Account account = new ro.teamnet.ou.domain.neo.Account();

        account.setJpaId(accountDTO.getId());
        account.setUsername(accountDTO.getLogin());
/*
        account.setOrganizationalUnit(accountDTO.);
*/
        return account;
    }

    public static AccountDTO toDTO(Account account) {

        AccountDTO accountDTO = new AccountDTO();

        return accountDTO;
    }

    public static AccountDTO toDTO(ro.teamnet.ou.domain.neo.Account account) {

        AccountDTO accountDTO = new AccountDTO();

        return accountDTO;
    }

}
