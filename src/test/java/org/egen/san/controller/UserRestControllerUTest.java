/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import static org.junit.Assert.*;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author san
 */
@RunWith(MockitoJUnitRunner.class)
@Transactional
public class UserRestControllerUTest {
    
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
        
        MvcResult result = mockMvc
                .perform(get("/api/v1/users/getAllUsers"))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaUtil.APPLICATION_JSON_UTF8))
                .andReturn();
        
        String contentString = result.getResponse().getContentAsString();
        
        ObjectMapper mapper = new ObjectMapper();
        List<User> returnedResult = mapper.readValue(contentString, 
                TypeFactory.defaultInstance()
                        .constructCollectionType(List.class, User.class));
        
        assertNotNull("Expected One User. Returned null", returnedResult);
        assertTrue("Expected One User. But not",returnedResult.size() == 1);
        assertTrue("Input and Output User dont match values", 
                User.isAllValueEquals(oneUserResult.get(0), returnedResult.get(0)));
    }
    
    @Test
    public void updateUser_WithUserNotInDB() throws Exception{
        User user = VALID_USERS.get(0);
        
        when(userService.updateUser(user)).thenReturn(null);
        
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(user);
        
        mockMvc
                .perform(
                    put("/api/v1/users/updateUser")
                        .contentType(MediaUtil.APPLICATION_JSON_UTF8)
                        .content(requestBody)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
    
    @Test
    public void updateUser_WithUserInDB() throws Exception{
        User user = VALID_USERS.get(0);
        
        User updatedUser = User.copyUserData(new User(), user);
        updatedUser.setId(user.getId());
        updatedUser.setLastName("Khan");
        
        when(userService.updateUser(user)).thenReturn(updatedUser);
        
        ObjectMapper mapper1 = new ObjectMapper();
        String requestBody = mapper1.writeValueAsString(user);
        
        MvcResult result = mockMvc
                .perform(
                    put("/api/v1/users/updateUser")
                        .contentType(MediaUtil.APPLICATION_JSON_UTF8)
                        .content(requestBody)
                )
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaUtil.APPLICATION_JSON_UTF8))
                .andReturn();
        
        String responseBodyAsString = result.getResponse().getContentAsString();
        
        ObjectMapper mapper2 = new ObjectMapper();
        User returnedUser = mapper2.readValue(responseBodyAsString, User.class);
        
        assertNotNull("Expected One User. Returned null", returnedUser);
        assertTrue("Input and Output User dont match values", 
                User.isAllValueEquals(updatedUser, returnedUser));
    }
    
    @Test
    public void createUser_WithValidUserNotInDB() throws Exception{
        User user = VALID_USERS.get(0);
        
        when(userService.findById(user.getId())).thenReturn(null);
        
        doNothing().when(userService).createUser(user);
        
        ObjectMapper mapper1 = new ObjectMapper();
        String requestBody = mapper1.writeValueAsString(user);
        
        mockMvc
                .perform(
                    post("/api/v1/users/createUser")
                        .contentType(MediaUtil.APPLICATION_JSON_UTF8)
                        .content(requestBody)
                )
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }
    
    @Test
    public void createUser_WithValidUserInDB() throws Exception{
        User user = VALID_USERS.get(0);
        
        when(userService.findById(user.getId())).thenReturn(user);
        
        doNothing().when(userService).createUser(user);
        
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(user);
        
        mockMvc
                .perform(
                    post("/api/v1/users/createUser")
                        .contentType(MediaUtil.APPLICATION_JSON_UTF8)
                        .content(requestBody)
                )
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(content().string(""));
    }
    
    @Test
    public void createUser_InValidUserNotInDB_WithNullUserId() throws Exception{
        User nullId_User = User.copyUserData(new User(), VALID_USERS.get(0));
        nullId_User.setId(null);
        
        when(userService.findById(nullId_User.getId())).thenReturn(null);
        
        doNothing().when(userService).createUser(nullId_User);
        
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(nullId_User);
        
        MvcResult result = mockMvc
                .perform(
                    post("/api/v1/users/createUser")
                        .contentType(MediaUtil.APPLICATION_JSON_UTF8)
                        .content(requestBody)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaUtil.APPLICATION_JSON_UTF8))
                .andReturn();
        
        String responseBodyAsString = result.getResponse().getContentAsString();
        
        "id cannot be null for User".equals(responseBodyAsString);
    }
    
    @Test
    public void createUser_InValidUserNotInDB_WithZeroAge() throws Exception{
        User zeroAge_User = User.copyUserData(new User(), VALID_USERS.get(0));
        zeroAge_User.setId(null);
        
        when(userService.findById(zeroAge_User.getId())).thenReturn(null);
        
        doNothing().when(userService).createUser(zeroAge_User);
        
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(zeroAge_User);
        
        MvcResult result = mockMvc
                .perform(
                    post("/api/v1/users/createUser")
                        .contentType(MediaUtil.APPLICATION_JSON_UTF8)
                        .content(requestBody)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaUtil.APPLICATION_JSON_UTF8))
                .andReturn();
        
        String responseBodyAsString = result.getResponse().getContentAsString();
        
        "Age should atleast be 1".equals(responseBodyAsString);
    }
    
}
