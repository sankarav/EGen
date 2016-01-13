/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.ArrayList;
import java.util.List;
import org.egen.san.config.TestAppConfig;
import org.egen.san.configuration.DAOConfig;
import org.egen.san.configuration.EmbeddedDBConfig;
import org.egen.san.configuration.ORMConfig;
import org.egen.san.controller.UserRestController;
import org.egen.san.dao.UserDAO;
import org.egen.san.model.User;
import org.egen.san.util.MediaUtil;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

/**
 *
 * @author san
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedDBConfig.class, ORMConfig.class, DAOConfig.class, TestAppConfig.class})
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
        List<User> OldUsers = userService.getAllUsers();
        int countOldUsers = (OldUsers == null) ? 0 : OldUsers.size();
        
        userService.createUser(VALID_USERS.get(0));
        
        List<User> returnedUsers = userService.getAllUsers();
        
        assertNotNull("Retrieved data is null. Expected One", returnedUsers);
        assertTrue("Expected One User. But size = " + returnedUsers.size(), returnedUsers.size() == (countOldUsers + 1));
        assertTrue("Retrieved user value is not as Expected", User.isAllValueEquals(VALID_USERS.get(0), returnedUsers.get(0)));

    }
    
}
