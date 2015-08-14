package ro.teamnet.ou.acl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ro.teamnet.ou.acl.aop.OrganizationalUnitHierarchyFilterAspect;

/**
 * Configuration class for creating aspect instance of type {@link OrganizationalUnitHierarchyFilterAspect}.
 */
@Configuration
@EnableAspectJAutoProxy
public class OrganizationalUnitHierarchyFilterAspectConfig {

    @Bean
    public OrganizationalUnitHierarchyFilterAspect organizationalUnitHierarchyFilterAspect() {
        return new OrganizationalUnitHierarchyFilterAspect();
    }
}
