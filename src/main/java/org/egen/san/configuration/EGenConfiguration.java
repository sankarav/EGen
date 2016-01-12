/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.configuration;

import org.egen.san.dao.UserDAO;
import org.egen.san.service.UserService;
import org.egen.san.service.UserServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author san
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.egen.san")
@EnableTransactionManagement
public class EGenConfiguration {

    @Autowired
    @Bean(name = "userService")
    public UserService getUserService(UserDAO userDAO) {
        //org.hsqldb.util.DatabaseManager.threadedDBM();
        return new UserServiceImpl(userDAO);
    }
    
    /*
    
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(getHibernateProperties());
        sessionBuilder.addAnnotatedClasses(User.class);

        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) 
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);

        return transactionManager;
    }
    
    @Autowired
    @Bean(name = "userDAO")
    public UserDAO getUserDAO(SessionFactory sessionFactory) {
        return new UserDAOImpl(sessionFactory);
    }
    
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("connection.pool_size", "1");
        properties.put("current_session_context_class", "thread");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        return properties;
    }
    */

}
