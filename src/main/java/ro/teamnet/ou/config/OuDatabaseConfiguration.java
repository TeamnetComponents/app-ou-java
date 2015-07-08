package ro.teamnet.ou.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.cross_store.config.CrossStoreNeo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ro.teamnet.bootstrap.extend.AppRepositoryFactoryBean;
import ro.teamnet.bootstrap.plugin.jpa.DefaultPackagesToScanPlugin;
import ro.teamnet.bootstrap.plugin.jpa.JpaPackagesToScanPlugin;
import ro.teamnet.neo.config.Neo4jBaseConfiguration;

import java.io.IOException;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "ro.teamnet.ou.repository.neo")
@EnableJpaRepositories(basePackages = {"ro.teamnet.ou.repository.jpa"},
        repositoryFactoryBeanClass = AppRepositoryFactoryBean.class)
@Import(Neo4jBaseConfiguration.class)
public class OuDatabaseConfiguration {

    private RelaxedPropertyResolver propertyResolver;
    private Environment env;

    @Bean
    public JpaPackagesToScanPlugin jpaPackagesToScanPlugin1() {
        JpaPackagesToScanPlugin ret = DefaultPackagesToScanPlugin
                .instance().addPackage("ro.teamnet.ou.domain.neo");


        return ret;
    }


}
