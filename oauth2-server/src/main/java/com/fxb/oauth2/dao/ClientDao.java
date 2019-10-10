package com.fxb.oauth2.dao;

import com.fxb.oauth2.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fangxiaobai on 2017/10/13 11:25.
 * @description
 */

@Repository
public interface ClientDao extends BaseDao<Client>{
    
    Object findBySecret(String clientSecret);
    
    Client findByClientId(String clientId);
}
