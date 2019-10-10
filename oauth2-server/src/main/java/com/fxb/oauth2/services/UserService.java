package com.fxb.oauth2.services;

import com.fxb.oauth2.PasswordHelper;
import com.fxb.oauth2.dao.UserDao;
import com.fxb.oauth2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fangxiaobai on 2017/10/11 20:52.
 * @description
 */
@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordHelper passwordHelper;
    
    public List<User> findAll() {
        return this.userDao.findAll();
    }
    
    public User findByUsername(String username) {
    return this.userDao.findByUsername(username);
    }
    
    public void addUser(User user){
        passwordHelper.encryptPassword(user);
        this.userDao.save(user);
    }
    
    public void delete(User user) {
        this.userDao.delete(user);
    }
}
