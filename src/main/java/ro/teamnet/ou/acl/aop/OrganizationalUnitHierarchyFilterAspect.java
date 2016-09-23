package ro.teamnet.ou.acl.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.acl.domain.OrganizationalUnitHierarchyEntity;
import ro.teamnet.ou.acl.service.OrganizationalUnitHierarchyFilterAdvice;

import javax.inject.Inject;

/**
 * Aspect that enables the filtering of entities extending {@link OrganizationalUnitHierarchyEntity} based on the
 * organizational units associated with the authenticated account.
 *
 */
@Aspect
@Order(10000)
public class OrganizationalUnitHierarchyFilterAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private OrganizationalUnitHierarchyFilterAdvice filterAdvice;

    @Pointcut("execution(* *..*Repository*.*(..))")
    public void onRepositoryMethods() {
    }

    @Pointcut("within(ro.teamnet.bootstrap.extend.AppRepositoryFactoryBean)" +
            "|| within(org.springframework*..*.*)")
    public void withinRepositoryBeansOrConfigurationClasses(){
    }

    @Pointcut("within(org.springframework.data.neo4j.repository.GraphRepository)")
    public void neo4jRepositoryMethods(){
    }

    @Before("onRepositoryMethods() && !withinRepositoryBeansOrConfigurationClasses() &&!neo4jRepositoryMethods()")
    public void aroundRepositoryMethods(JoinPoint jp) throws Throwable {
//        log.debug("before repository method - begin");
        if (filterAdvice == null || !(jp.getTarget() instanceof AppRepository)) {
            return;
        }
        filterAdvice.setupOrganizationalUnitHierarchyFilter();
    }
}
