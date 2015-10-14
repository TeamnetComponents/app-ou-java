package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.repository.jpa.AccountFunctionRepository;

import javax.inject.Inject;

/**
 * Created by Marian.Spoiala on 10/14/2015.
 */
@Service
public class AccountFunctionServiceImpl implements AccountFunctionService {

    @Inject
    AccountFunctionRepository accountFunctionRepository;

    @Override
    public AccountFunction findByAccountIdAndFunctionId(Long accountId, Long functionId) {
        return accountFunctionRepository.findByAccountIdAndFunctionId(accountId, functionId);
    }
}
