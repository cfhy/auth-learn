package com.fxb.oauth2.dao;

import com.fxb.oauth2.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fangxiaobai on 2017/10/11 20:52.
 * @description
 */
@Repository
public interface UserDao extends  BaseDao<User> {
    
    User findByUsername(String username);
}
