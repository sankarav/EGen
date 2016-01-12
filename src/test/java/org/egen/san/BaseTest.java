/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san;

import org.egen.san.config.TestAppConfig;
import org.egen.san.configuration.DAOConfig;
import org.egen.san.configuration.EmbeddedDBConfig;
import org.egen.san.configuration.ORMConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author san
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedDBConfig.class, ORMConfig.class, DAOConfig.class, TestAppConfig.class})
public class BaseTest {
    
    @Test
    public void dummyTest(){
        
    }
    
}
