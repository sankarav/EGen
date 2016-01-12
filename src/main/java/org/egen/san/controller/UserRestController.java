/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.egen.san.model.User;
import org.egen.san.util.ErrorMsg;
import org.egen.san.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * UserRestController is the Controller to handle all the rest api calls for User
 * 
 * @author san
 */
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestController {
    
    private UserService userService;
    
    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }
    
    private ValidatorFactory validationFactory = Validation.buildDefaultValidatorFactory();
    
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ResponseEntity<List<String>> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

        List<String> constraintViolationMessages = validateCreateUserRequest(user);
        
        if(constraintViolationMessages != null){
            return new ResponseEntity<>(constraintViolationMessages, HttpStatus.CONFLICT);
        } else {
            if(userService.findById(user.getId()) != null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                userService.createUser(user);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(ucBuilder.path("/createUser/{id}").buildAndExpand(user.getId()).toUri());
                return new ResponseEntity<>(headers, HttpStatus.CREATED);
            }
        }
    }
    
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        
        User updatedUserInfo = userService.updateUser(user);
 
        if(updatedUserInfo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(updatedUserInfo, HttpStatus.OK);
        }
    }
    
    private List<String> validateCreateUserRequest(User _user){
        Validator validator = validationFactory.getValidator();
        Set<ConstraintViolation<User>> contraintViolations = validator.validate(_user);
        
        if(contraintViolations.isEmpty()){
            return null;
        } else {
            return buildConstraintViolationMessage(contraintViolations);
        }
    }
    
    private List<String> buildConstraintViolationMessage(Set<ConstraintViolation<User>> contraintViolations){
        List<String> out = new ArrayList<>();
        for(ConstraintViolation<User> contraintViolation : contraintViolations){
            out.add(getConstraintViolationMessage(contraintViolation));
        }
        return out;
    }
    
    private String getConstraintViolationMessage(ConstraintViolation<User> contraintViolation){
        return contraintViolation.getMessage();
    }
}
