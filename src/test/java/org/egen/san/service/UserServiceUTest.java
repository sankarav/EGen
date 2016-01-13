/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.service;

import java.util.ArrayList;
import java.util.List;
import org.egen.san.config.TestAppConfig;
import org.egen.san.configuration.DAOConfig;
import org.egen.san.configuration.EmbeddedDBConfig;
import org.egen.san.configuration.ORMConfig;
import org.egen.san.model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author san
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedDBConfig.class, ORMConfig.class, DAOConfig.class, TestAppConfig.class})
@Transactional
public class UserServiceUTest {
    
    @Autowired
    UserService userService;
    
    private static List<User> VALID_USERS;
    
    @BeforeClass
    public static void setupTestClass(){
        VALID_USERS = new ArrayList<>();
        
        VALID_USERS.add(new User("090-1213-90ba-129b-990", 
                "Amitab", "", "Batchan",
                (short)65, 'M', 1289019999L, "12345"));
        
        VALID_USERS.add(new User("000-0000-000a-000b-000", 
                "Sankar", "V", "Dhandapani",
                (short)30, 'M', 9099019999L, "90145"));
    }
    
    @AfterClass
    public static void tearDownTestClass(){
        VALID_USERS = null;
    }
    
    @Test
    public void getAllUsersTest(){
        
        userService.createUser(VALID_USERS.get(0));
        
        List<User> returnedUsers = userService.getAllUsers();
        
        assertNotNull("Retrieved data is null. Expected One", returnedUsers);
        assertTrue("Expected One User. But size = " + returnedUsers.size(), returnedUsers.size() == 1);
        assertTrue("Retrieved user value is not as Expected", User.isAllValueEquals(VALID_USERS.get(0), returnedUsers.get(0)));

    }
    
}
