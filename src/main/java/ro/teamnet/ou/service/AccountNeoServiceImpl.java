package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.repository.neo.AccountNeoRepository;

import javax.inject.Inject;

@Service
public class AccountNeoServiceImpl implements AccountNeoService {

    @Inject
    private AccountNeoRepository accountNeoRepository;


    @Override
    public void save(Account accountJPA) {
        ro.teamnet.ou.domain.neo.Account accountNeo = accountNeoRepository.findByJpaId(accountJPA.getId());
        if (accountNeo == null) {
            accountNeo = new ro.teamnet.ou.domain.neo.Account();
        }
        accountNeo.setJpaId(accountJPA.getId());
        accountNeo.setUsername(accountJPA.getLogin());
        accountNeoRepository.save(accountNeo);
    }
}
