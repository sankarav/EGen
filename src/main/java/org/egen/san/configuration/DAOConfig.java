/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.configuration;

import org.egen.san.dao.UserDAO;
import org.egen.san.dao.UserDAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author san
 */
@Configuration
@EnableTransactionManagement
public class DAOConfig {
    
    @Autowired
    @Bean(name = "userDAO")
    public UserDAO getUserDAO(SessionFactory sessionFactory) {
        return new UserDAOImpl(sessionFactory);
    }
    
}
