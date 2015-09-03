package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.service.AbstractService;
import ro.teamnet.ou.domain.neo.Account;

import java.util.List;

/**
 * Created by Marian.Spoiala on 9/3/2015.
 */
public interface AccountNeoService {
    Account save(Account t);

    /*List<Account> findAll();

    Account findOne(Long id);

    void delete(Long id);*/
}
