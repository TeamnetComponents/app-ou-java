package ro.teamnet.ou.acl.service;

import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.web.rest.dto.AccountDTO;

/**
 * Created by Ionut.Patrascu on 9/4/2015.
 */
public interface AccountNeoService {
    void save(Account accountJPA);
}
