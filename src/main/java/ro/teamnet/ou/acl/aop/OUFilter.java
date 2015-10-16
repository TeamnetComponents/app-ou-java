package ro.teamnet.ou.acl.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Marian.Spoiala on 10/10/2015.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface OUFilter {
    String value() default "NOT_SET";
}
