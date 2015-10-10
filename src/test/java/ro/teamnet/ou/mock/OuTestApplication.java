package ro.teamnet.ou.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import ro.teamnet.bootstrap.config.*;
import ro.teamnet.bootstrap.config.apidoc.SwaggerConfiguration;
import ro.teamnet.bootstrap.config.metrics.JHipsterHealthIndicatorConfiguration;
import ro.teamnet.bootstrap.plugin.security.UserAuthorizationPlugin;
import ro.teamnet.bootstrap.plugin.upload.FileServicePlugin;
import ro.teamnet.ou.acl.config.OrganizationalUnitHierarchyFilterAspectConfig;
import ro.teamnet.ou.config.AccountFilterAspectConfig;

/**
 * This is a helper Java class that provides an alternative to creating a web.xml.
 * A main method is also provided and may be used for running the application.
 */
@ComponentScan(
        basePackages = {"ro.teamnet.bootstrap", "ro.teamnet.ou", "ro.teamnet.neo"
        },
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = {/* Components you wish to exclude */
                        OrganizationalUnitHierarchyFilterAspectConfig.class, AccountFilterAspectConfig.class,
                        SwaggerConfiguration.class, WebConfigurer.class, WebSecurityConfiguration.class,
                        ThymeleafConfiguration.class, AsyncConfiguration.class, LoggingAspectConfiguration.class,
                        JHipsterHealthIndicatorConfiguration.class, CloudDatabaseConfiguration.class, LocaleConfiguration.class,
                }
        )
)
@EnableAutoConfiguration(
        exclude = {
                MetricFilterAutoConfiguration.class,
                MetricRepositoryAutoConfiguration.class,
                DatabaseConfiguration.class
        }
)
@EnablePluginRegistries({UserAuthorizationPlugin.class, FileServicePlugin.class})
@SpringBootApplication
public class OuTestApplication {
    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OuTestApplication.class);
        app.setShowBanner(false);

        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

        // Check if the selected profile has been set as argument.
        // if not the development profile will be added
        addDefaultProfile(app, source);
        addLiquibaseScanPackages();
        app.run(args);
    }

    /**
     * Set a default profile if it has not been set
     */
    protected static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active")) {
            app.setAdditionalProfiles("test");
        }
    }

    /**
     * Set the liquibases.scan.packages to avoid an exception from ServiceLocator.
     */
    protected static void addLiquibaseScanPackages() {
        System.setProperty("liquibase.scan.packages", "liquibase.change" + "," + "liquibase.database" + "," +
                "liquibase.parser" + "," + "liquibase.precondition" + "," + "liquibase.datatype" + "," +
                "liquibase.serializer" + "," + "liquibase.sqlgenerator" + "," + "liquibase.executor" + "," +
                "liquibase.snapshot" + "," + "liquibase.logging" + "," + "liquibase.diff" + "," +
                "liquibase.structure" + "," + "liquibase.structurecompare" + "," + "liquibase.lockservice" + "," +
                "liquibase.ext" + "," + "liquibase.changelog");
    }

}
