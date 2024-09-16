package com.dev.superapp.config.datasource.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Properties;


/**
 * @author jayant
 * This class contains DB config for Local DB (MySQL)   Lenskart Style
 * -> basePackages : contains repository package paths to scan means these tables will be under this dataSource
 *
 */
@Configuration
@ConfigurationProperties("spring.datasource.local-database")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.dev.superapp.dao.local"},
        entityManagerFactoryRef = "localDBEntityManagerFactory",
        transactionManagerRef = "localDBTransactionManager"
)
public class LocalDbConfig extends HikariConfig {
    private static final Properties jpaProperties = new Properties();

    static {
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("show-sql", "true");
        jpaProperties.put("hibernate.ddl-auto", "none");
    }

    @Value("${local-database.maxAliveConnections:10}")
    int maxPoolSizeForDataSource;
    @Value("${local-database.minIdleConnections:5}")
    int minPoolSizeForDataSource;
    @Value("${local-database.db.name:local}")
    String localDB;

    @Bean
    public HikariDataSource dataSourceLocalDb() {
        return new HikariDataSource(this);
    }

    @Bean(name = "localDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(
            final HikariDataSource dataSourceLocalDb) {
        dataSourceLocalDb.setMaximumPoolSize(maxPoolSizeForDataSource);
        dataSourceLocalDb.setMinimumIdle(minPoolSizeForDataSource);
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSourceLocalDb);
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPersistenceUnitName(localDB);
        factoryBean.setPackagesToScan("com.dev.superapp.model.local");    // can enter multiple model class packages ("package1", "package2")
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Bean(name = "localDBTransactionManager")
    public PlatformTransactionManager localTransactionManager
            (@Qualifier("localDBEntityManagerFactory") EntityManagerFactory localEntityManagerFactory) {
        return new JpaTransactionManager(localEntityManagerFactory);
    }
}