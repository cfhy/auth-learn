package com.fxb.oauth2.dao;

import com.fxb.oauth2.entity.Client;
import com.fxb.oauth2.entity.User;

import java.util.List;

/**
 * @author fangxiaobai on 2017/10/13 11:26.
 * @description
 */
public interface BaseDao<T> {
    
    List<T> findAll();
    
    void delete(T t);
    
    void update(T t);
    
    void save(T t);
    
    /**
     * 查询
     * @param id
     * @return T
     */
    T findById(String id);
}
