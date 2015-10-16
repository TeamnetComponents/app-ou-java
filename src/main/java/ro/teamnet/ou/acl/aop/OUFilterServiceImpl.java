package ro.teamnet.ou.acl.aop;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Marian.Spoiala on 9/22/2015.
 */
@Component("ouFilterService")
public class OUFilterServiceImpl implements OUFilterService {

    @Inject
    @Qualifier("oUFilterPluginRegistry")
    private PluginRegistry<OUFilterPlugin, String> ouFilterPluginRegistry;

    public Boolean isObjectAllowed(String filterValue, Object obj) {

        List<OUFilterPlugin> ouFilterPlugins =
                ouFilterPluginRegistry.getPluginsFor(filterValue);


        if (ouFilterPlugins != null) {
            for (OUFilterPlugin ouFilterPlugin : ouFilterPlugins) {
                return ouFilterPlugin.isObjectAllowed(obj);
            }
        }

        //By default it will not be filtered if no plugin found for object
        return true;
    }

    @Override
    public Object filterObjects(String filterValue, Object obj) {
        List<OUFilterPlugin> ouFilterPlugins =
                ouFilterPluginRegistry.getPluginsFor(filterValue);


        if (ouFilterPlugins != null) {
            for (OUFilterPlugin ouFilterPlugin : ouFilterPlugins) {
                return ouFilterPlugin.filterObjects(obj);
            }
        }

        //By default it will not be filtered if no plugin found for object
        return obj;
    }

    public Boolean isObjectSaveAllowed(String filterValue, Object obj) {
        List<OUFilterPlugin> ouFilterPlugins =
                ouFilterPluginRegistry.getPluginsFor(filterValue);


        if (ouFilterPlugins != null) {
            for (OUFilterPlugin ouFilterPlugin : ouFilterPlugins) {
                return ouFilterPlugin.isObjectSaveAllowed(obj);
            }
        }

        //By default it will not be filtered if no plugin found for object
        return true;
    }

    @Override
    public Boolean isObjectDeleteAllowed(String filterValue, Object obj) {
        List<OUFilterPlugin> ouFilterPlugins =
                ouFilterPluginRegistry.getPluginsFor(filterValue);


        if (ouFilterPlugins != null) {
            for (OUFilterPlugin ouFilterPlugin : ouFilterPlugins) {
                return ouFilterPlugin.isObjectDeleteAllowed(obj);
            }
        }

        //By default it will not be filtered if no plugin found for object
        return true;
    }
}
