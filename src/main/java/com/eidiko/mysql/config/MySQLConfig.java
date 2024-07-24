package com.eidiko.mysql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.eidiko.mysql.repository",
        entityManagerFactoryRef = "mySQLEntityManagerFactoryBean",
        transactionManagerRef = "mySQLTransactionManager"
)
@EnableTransactionManagement
public class MySQLConfig {

    @Bean
    @ConfigurationProperties(
            prefix = "spring.datasource.mysql"
    )
    public DataSourceProperties mySQLDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource mySQLDataSource() {
        return mySQLDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mySQLEntityManagerFactoryBean(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("mySQLDataSource") DataSource dataSource
    ) {
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.eidiko.mysql.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager mySQLTransactionManager(
            @Qualifier("mySQLEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean factoryBean
    ) {
        return new JpaTransactionManager(factoryBean.getObject());
    }

}
