package ro.teamnet.ou.acl.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Marian.Spoiala on 10/10/2015.
 */
@Service
@Aspect
public class GenericOUResourceFilterAspect {

    @Inject
    @Qualifier(value = "ouFilterService")
    private OUFilterService ouFilterService;

    @Around("@annotation(ro.teamnet.ou.acl.aop.OUFilter)")
    public Object handleOrgUnitFilter(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        OUFilter ouFilter = method.getAnnotation(OUFilter.class);
        String filterValue = ouFilter.value();

        if (filterValue.equals("NOT_SET")) {
            return pjp.proceed();
        }

        if (pjp.getSignature().getName().toLowerCase().contains("save") ||
                pjp.getSignature().getName().toLowerCase().contains("create") ||
                pjp.getSignature().getName().toLowerCase().contains("update")) {

            if (!ouFilterService.isObjectSaveAllowed(filterValue, pjp.getArgs())) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } else if (pjp.getSignature().getName().toLowerCase().contains("delete")) {
            if (!ouFilterService.isObjectDeleteAllowed(filterValue, pjp.getArgs())) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        }

        Object resultObj = pjp.proceed();

        if (resultObj instanceof ResponseEntity && ((ResponseEntity) resultObj).hasBody()) {
            resultObj = ((ResponseEntity) resultObj).getBody();
        }

        if (resultObj instanceof Collection || resultObj instanceof Map) {
            Object finalObj = ouFilterService.filterObjects(filterValue, resultObj);
            return new ResponseEntity<>(finalObj, HttpStatus.OK);
        } else if (!ouFilterService.isObjectAllowed(filterValue, resultObj)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (resultObj instanceof ResponseEntity) {
            return resultObj;
        }

        return new ResponseEntity<>(resultObj, HttpStatus.OK);
    }
}
