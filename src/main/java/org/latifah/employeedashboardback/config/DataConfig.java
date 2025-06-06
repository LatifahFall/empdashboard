package org.latifah.employeedashboardback.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableJpaRepositories("org.latifah.employeedashboardback.repository")
public class DataConfig {

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(env.getProperty("driver"));
        ds.setJdbcUrl(env.getProperty("url"));
        ds.setUsername(env.getProperty("user"));
        ds.setPassword(env.getProperty("password"));
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.latifah.employeedashboardback.entity");
        em.setJpaProperties(hibernateProperties());
        em.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
        return em;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return props;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        return tm;
    }
}
