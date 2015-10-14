package ro.teamnet.ou.service;

import ro.teamnet.ou.domain.jpa.AccountFunction;

/**
 * Created by Marian.Spoiala on 10/14/2015.
 */
public interface AccountFunctionService {

    AccountFunction findByAccountIdAndFunctionId(Long accountId, Long functionId);

}
