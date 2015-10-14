package ro.teamnet.ou.acl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import ro.teamnet.ou.acl.aop.OUFilterPlugin;

/**
 * Created by Marian.Spoiala on 10/10/2015.
 */
@Configuration
@EnablePluginRegistries({OUFilterPlugin.class})
public class OUFilterConfig {
}
