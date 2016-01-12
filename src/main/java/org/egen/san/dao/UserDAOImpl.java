/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.dao;

import java.util.List;
import org.egen.san.model.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author san
 */
@Repository
public class UserDAOImpl implements UserDAO{

    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    public UserDAOImpl(){
        
    }
     
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    @Transactional
    public void createUser(User _user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(_user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("from User").list();
        return users;
    }

    @Override
    @Transactional
    public void updateUser(User _user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(_user);
    }

    @Override
    @Transactional
    public User findById(String _id) {
        String hql = "from User where id = :id";
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setString("id", _id);
        
        List<User> listUser = (List<User>) query.list();
        
        if (listUser != null && !listUser.isEmpty()) {
            return listUser.get(0);
        }
        
        return null;
    }
    
}
