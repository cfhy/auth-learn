package com.fxb.oauth2.entity;

/**
 * @author fangxiaobai on 2017/10/11 20:54.
 * @description
 */
public class User {
    
    private String id;
    private String username;
    private String password;
    private String salt;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getCredentialSalt() {
        return username+salt;
    }
}
