package ro.teamnet.ou.mapper;


import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.web.rest.dto.AccountDTO;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import java.util.HashSet;

/**
 * Created by Marian.Spoiala on 8/19/2015.
 */
public class AccountMapper {

    public static ro.teamnet.ou.domain.neo.Account toNeo(Account accountJPA) {
        if (accountJPA == null) {
            return null;
        }
        ro.teamnet.ou.domain.neo.Account account = new ro.teamnet.ou.domain.neo.Account();
        account.setJpaId(accountJPA.getId());
        account.setUsername(accountJPA.getLogin());

        return account;
    }

    public static ro.teamnet.ou.domain.neo.Account toNeo(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        ro.teamnet.ou.domain.neo.Account account = new ro.teamnet.ou.domain.neo.Account();
        account.setJpaId(accountDTO.getId());
        account.setUsername(accountDTO.getUsername());
        return account;
    }

    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(account.getLogin());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setLastName(account.getLastName());
        accountDTO.setFirstName(account.getFirstName());
        accountDTO.setFunctions(new HashSet<FunctionDTO>());
        accountDTO.setAvailableFunctions(new HashSet<FunctionDTO>());
        return accountDTO;
    }

    public static AccountDTO toDTO(ro.teamnet.ou.domain.neo.Account account) {
        if (account == null) {
            return null;
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getJpaId());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setFunctions(new HashSet<FunctionDTO>());
        accountDTO.setAvailableFunctions(new HashSet<FunctionDTO>());
        return accountDTO;
    }

}