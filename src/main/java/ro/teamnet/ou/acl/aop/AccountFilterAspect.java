package ro.teamnet.ou.acl.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.ou.acl.service.AccountNeoService;

import javax.inject.Inject;

/**
 *  Aspect used for intercepting Account data save or update actions and save it in Neo4j database also.
 */

@Aspect
public class AccountFilterAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    public AccountNeoService accountNeoService;

    @Pointcut("execution(* ro.teamnet.bootstrap.repository.AccountRepository.save(..))")
    public void onAccountRepositoryMethods(){
        log.debug("before AccountRepositoryMethods - begin: ");
    }

    @AfterReturning(pointcut = "onAccountRepositoryMethods()", returning = "result")
    public void afterAccountRepositoryMethods(final JoinPoint joinPoint, final Object result){
        Account account = (Account)result;
        accountNeoService.save(account);
    }
}
