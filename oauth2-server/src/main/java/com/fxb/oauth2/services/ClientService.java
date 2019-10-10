package com.fxb.oauth2.services;

import com.fxb.oauth2.dao.ClientDao;
import com.fxb.oauth2.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fangxiaobai on 2017/10/13 11:28.
 * @description
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientDao clientDao;
    
    public List<Client> findAll(){
        return this.clientDao.findAll();
    }
    
    
    public void save(Client client) {
        this.clientDao.save(client);
    }
    
    public void delete(Client client) {
        this.clientDao.delete(client);
    }
    
    public Client findByClientId(String clientId) {
        return this.clientDao.findByClientId(clientId);
    }
    
    
    public Object findByClientSecret(String clientSecret) {
        return this.clientDao.findBySecret(clientSecret);
    }
}
