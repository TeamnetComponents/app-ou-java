package ro.teamnet.ou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.service.AccountService;
import ro.teamnet.ou.mapper.AccountMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Marian.Spoiala on 9/3/2015.
 */
@Configuration
public class SynchronizeDBs {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    AccountService accountService;

    @Inject
    AccountNeoService accountNeoService;

    @Bean
    @Order(10000)
    public boolean syncAccounts() {
        List<Account> accountJPAList = accountService.findAll();

        for (Account accountJPA : accountJPAList) {
            log.debug("Creating Account in Neo : " + accountJPA.getLogin());
            ro.teamnet.ou.domain.neo.Account accountNeo = AccountMapper.toNeo(accountJPA);
            accountNeoService.save(accountNeo);
        }

        return true;
    }
}
