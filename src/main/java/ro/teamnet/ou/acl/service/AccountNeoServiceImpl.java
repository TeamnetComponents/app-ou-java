package ro.teamnet.ou.acl.service;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.repository.neo.AccountNeoRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.service.OUAccountService;

import javax.inject.Inject;

@Service
public class AccountNeoServiceImpl implements AccountNeoService {

    @Inject
    AccountNeoRepository accountNeoRepository;

    @Inject
    OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    @Inject
    OUAccountService ouAccountService;

    @Override
    public void save(Account accountJPA) {
        ro.teamnet.ou.domain.neo.Account accountNeo = accountNeoRepository.findByJpaId(accountJPA.getId());
        if(accountNeo == null){
            accountNeo = new ro.teamnet.ou.domain.neo.Account();
        }
        accountNeo.setJpaId(accountJPA.getId());
        accountNeo.setUsername(accountJPA.getLogin());
        accountNeoRepository.save(accountNeo);
    }
}
