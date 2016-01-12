/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.service;

import java.util.ArrayList;
import java.util.List;
import org.egen.san.dao.UserDAO;
import org.egen.san.model.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author san
 */
@Service
public class UserServiceImpl implements UserService{

    private UserDAO userDAO;
 
    public UserServiceImpl() {
    
    }
    
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Override
    //@Transactional
    public void createUser(User _user) {
        this.userDAO.createUser(_user);
    }

    @Override
    //@Transactional
    public List<User> getAllUsers() {
        return this.userDAO.getAllUsers();
    }

    @Override
    //@Transactional
    public User updateUser(User _user) {
        User currUser = findById(_user.getId());
        if(currUser != null){
            User.copyUserData(currUser, _user);
            this.userDAO.updateUser(currUser);
        }
        return currUser;
    }
    
    @Override
    //@Transactional
    public User findById(String _id) {
        return this.userDAO.findById(_id);
    }

}
