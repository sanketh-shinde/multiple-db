package com.eidiko.postgresql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        basePackages = "com.eidiko.postgresql.repository",
        entityManagerFactoryRef = "postgresSQLEntityManagerFactoryBean",
        transactionManagerRef = "postgresSQLTransactionManager"
)
@EnableTransactionManagement
public class PostgresSQLConfig {

    @Bean
    @ConfigurationProperties(
            prefix = "spring.datasource.postgres"
    )
    public DataSourceProperties postgresSQlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource postgresSQLDataSource() {
        return postgresSQlDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresSQLEntityManagerFactoryBean(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("postgresSQLDataSource") DataSource dataSource
    ) {
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.eidiko.postgresql.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager postgresSQLTransactionManager(
            @Qualifier("postgresSQLEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean factoryBean
    ) {
        return new JpaTransactionManager(factoryBean.getObject());
    }
}
