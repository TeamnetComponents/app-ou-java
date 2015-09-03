package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import ro.teamnet.ou.domain.neo.Account;
import ro.teamnet.ou.repository.neo.AccountNeoRepository;

import javax.inject.Inject;

/**
 * Created by Marian.Spoiala on 9/3/2015.
 */
@Service
public class AccountNeoServiceImpl implements AccountNeoService{

    @Inject
    AccountNeoRepository accountNeoRepository;

    @Override
    public Account save(Account account) {
        return accountNeoRepository.save(account);
    }
}
