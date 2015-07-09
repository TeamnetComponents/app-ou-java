package ro.teamnet.ou.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ro.teamnet.bootstrap.extend.AppRepositoryFactoryBean;
import ro.teamnet.bootstrap.plugin.jpa.DefaultPackagesToScanPlugin;
import ro.teamnet.bootstrap.plugin.jpa.JpaPackagesToScanPlugin;
import ro.teamnet.neo.config.Neo4jBaseConfiguration;
import ro.teamnet.neo.plugin.DefaultNeoPackagesToScanPlugin;
import ro.teamnet.neo.plugin.Neo4jResourcePropertiesPathPlugin;
import ro.teamnet.neo.plugin.NeoPackagesToScanPlugin;
import ro.teamnet.ou.plugin.OuNeo4jResourcePropertiesPathPlugin;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "ro.teamnet.ou.repository.neo")
@EnableJpaRepositories(basePackages = {"ro.teamnet.ou.repository.jpa"},
        repositoryFactoryBeanClass = AppRepositoryFactoryBean.class)
@Import(Neo4jBaseConfiguration.class)
public class OuDatabaseConfiguration {



    @Bean
    public JpaPackagesToScanPlugin jpaPackagesToScanPlugin() {
        return DefaultPackagesToScanPlugin
                .instance().addPackage("ro.teamnet.ou.domain.jpa");
    }

    @Bean
    public NeoPackagesToScanPlugin neoPackagesToScanPlugin() {
        return DefaultNeoPackagesToScanPlugin
                .instance().addPackage("ro.teamnet.ou.domain.neo");
    }

    @Bean
    @Order(100)
    public Neo4jResourcePropertiesPathPlugin ouNeo4jResourcePropertiesPathPlugin(){
        return new OuNeo4jResourcePropertiesPathPlugin();
    }




}
