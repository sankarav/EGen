/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.platform;

import org.egen.san.BaseTest;
import org.egen.san.dao.UserDAO;
import org.egen.san.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This test class will ensure if Embedded DB is up and running fine
 * @author san
 */
public class EmbeddedDBTest extends BaseTest{
    
    @Autowired
    UserDAO userDAO;
    
    private User testDBUser;
    
    @Before
    public void setupTestCase() {
        testDBUser = new User("U2", "San", null, "Dhandapani2", (short)99, 'M', 
                                12345678l, "33444");
    }
    
    @After
    public void tearDownTestCase() {
        testDBUser = null;
    }
    
    @Test
    public void testIfEmbeddedDBWorks(){
        userDAO.createUser(testDBUser);
        User retrievedUserfromDB = userDAO.findById("U2");
        
        Assert.assertNotNull("Retrieved data is null. EmbeddedDB not working", retrievedUserfromDB);
        Assert.assertEquals("Retrieved data isnt the same inserted", testDBUser.getId(), retrievedUserfromDB.getId());
    }
    
}
