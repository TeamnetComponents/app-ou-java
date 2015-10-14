package ro.teamnet.ou.acl.aop;

import org.springframework.plugin.core.Plugin;

/**
 * Created by Marian.Spoiala on 10/10/2015.
 */
public interface OUFilterPlugin extends Plugin<String> {

    public Boolean isObjectAllowed(Object obj);

    Object filterObjects(Object obj);

    public Boolean isObjectSaveAllowed(Object obj);

    public Boolean isObjectDeleteAllowed(Object obj);
}
