/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.config;

import org.egen.san.dao.UserDAO;
import org.egen.san.service.UserService;
import org.egen.san.service.UserServiceImpl;
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
public class TestAppConfig {
    
    @Autowired
    @Bean(name = "userService")
    public UserService getUserService(UserDAO userDAO) {
        //org.hsqldb.util.DatabaseManager.threadedDBM();
        return new UserServiceImpl(userDAO);
    }
    
}
