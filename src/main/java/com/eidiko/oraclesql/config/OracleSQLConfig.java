package com.eidiko.oraclesql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.eidiko.oraclesql.repository",
        entityManagerFactoryRef = "oracleSQLEntityManagerFactoryBean",
        transactionManagerRef = "oracleSQLTransactionManager"
)
@EnableTransactionManagement
public class OracleSQLConfig {

    @Bean
    @ConfigurationProperties(
            prefix = "spring.datasource.oracle"
    )
    public DataSourceProperties oracleSQLDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource oracleSQLDataSource() {
        return oracleSQLDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean oracleSQLEntityManagerFactoryBean(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("oracleSQLDataSource") DataSource dataSource
    ) {
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.eidiko.oraclesql.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager oracleSQLTransactionManager(
            @Qualifier("oracleSQLEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean factoryBean
    ) {
        return new JpaTransactionManager(factoryBean.getObject());
    }

}
