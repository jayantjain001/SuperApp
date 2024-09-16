package com.dev.superapp.config.datasource.mysql;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;


@Configuration
@EnableTransactionManagement
@ConfigurationProperties(prefix = "spring.datasource.super-app")
@EnableJpaRepositories(
        basePackages = "com.dev.superapp.dao.super_app",
        entityManagerFactoryRef = "superAppEntityManagerFactory",
        transactionManagerRef = "superAppTransactionManager"
)
public class SuperAppDbConfig extends HikariConfig {

    private static final Properties jpaProperties = new Properties();

    static {
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("show-sql", "true");
        jpaProperties.put("hibernate.ddl-auto", "none");
    }

    @Bean
    public HikariDataSource superAppDataSource() {
        return new HikariDataSource(this);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean superAppEntityManagerFactory(
            @Qualifier("superAppDataSource") HikariDataSource dataSource) {


        // property to safeguard db from getting overloaded
        dataSource.setMaximumPoolSize(10);  //setting max no of parallel connections that 1 app instance can open

        // property to scale sql queries so that less opening and closing of db resources happen
        dataSource.setMinimumIdle(8);     //setting min no of parallel connections that 1 app instance will maintain at any given time
        dataSource.setConnectionTimeout(60000);   // max wait time to acquire a connection default : 30 sec , otherwise a SQL exception is thrown if wait time is exceeded
        dataSource.setKeepaliveTime(300000); // time for an idle connection to be kept alive : 5 mins set


        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPersistenceUnitName("super_app");
        factoryBean.setPackagesToScan("com.dev.superapp.model.super_app");    // can enter multiple model class packages ("package1", "package2")
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager superAppTransactionManager(
            @Qualifier("superAppEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
