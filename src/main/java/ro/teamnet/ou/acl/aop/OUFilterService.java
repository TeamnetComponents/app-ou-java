package ro.teamnet.ou.acl.aop;

/**
 * Created by Marian.Spoiala on 10/11/2015.
 */
public interface OUFilterService {

    Boolean isObjectAllowed(String filterValue, Object obj);

    Object filterObjects(String filterValue, Object obj);

    Boolean isObjectSaveAllowed(String filterValue, Object obj);

    Boolean isObjectDeleteAllowed(String filterValue, Object obj);
}
