/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.List;
import org.egen.san.model.User;
import org.egen.san.service.UserService;
import org.egen.san.util.MediaUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author san
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerStandAloneTest {
    
    @Mock
    UserService userService;
    
    MockMvc mockMvc;
    
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
    
    @Before
    public void setupTestCase(){
        mockMvc = MockMvcBuilders
                    .standaloneSetup(new UserRestController(userService))
                    .build();
    }
    
    @Test
    public void getAllUsers_WithEmptyDB() throws Exception{
        when(userService.getAllUsers()).thenReturn(new ArrayList<User>());
        
        mockMvc
                .perform(get("/api/v1/users/getAllUsers"))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(""));
    }
    
    @Test
    public void getAllUsers_WithOneUserInDB() throws Exception{
        ArrayList<User> oneUserResult = new ArrayList<>();
        oneUserResult.add(VALID_USERS.get(0));
        
        when(userService.getAllUsers()).thenReturn(oneUserResult);
        
        mockMvc
                .perform(get("/api/v1/users/getAllUsers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaUtil.APPLICATION_JSON_UTF8));

    }
    
}
