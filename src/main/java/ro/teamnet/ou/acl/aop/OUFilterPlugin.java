package ro.teamnet.ou.acl.aop;

import org.springframework.plugin.core.Plugin;

/**
 * Created by Marian.Spoiala on 10/10/2015.
 */
public interface OUFilterPlugin extends Plugin<String> {

    Boolean isObjectAllowed(Object obj);

    Object filterObjects(Object obj);

    Boolean isObjectSaveAllowed(Object obj);

    Boolean isObjectDeleteAllowed(Object obj);
}
