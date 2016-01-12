/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.dao;

import java.util.List;
import org.egen.san.model.User;

/**
 *
 * @author san
 */
public interface UserDAO {
    
    void createUser(User user);
    List<User> getAllUsers();
    void updateUser(User user);
    User findById(String id);
    
}
