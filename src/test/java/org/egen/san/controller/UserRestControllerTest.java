/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.controller;

import org.egen.san.config.TestAppConfig;
import org.egen.san.configuration.DAOConfig;
import org.egen.san.configuration.EGenConfiguration;
import org.egen.san.configuration.EmbeddedDBConfig;
import org.egen.san.configuration.ORMConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author san
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EGenConfiguration.class})
@WebAppConfiguration
public class UserRestControllerTest {
    
    @Autowired
    WebApplicationContext wac;
    
    MockMvc mockMvc;
    
    public void setupTestCase(){
        mockMvc = MockMvcBuilders
                    //.standaloneSetup(new UserRestController(null))
                    .webAppContextSetup(wac)
                    .build();
    }
    
    
    @Test
    public void doSomething(){
        
    }
    
}
