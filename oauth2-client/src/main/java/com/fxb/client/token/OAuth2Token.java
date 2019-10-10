package com.fxb.client.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author fangxiaobai on 2017/10/13 16:39.
 * @description
 *
 *         自定义了 OAuth2 使用 token(令牌)
 */
public class OAuth2Token implements AuthenticationToken{
    
    private String authCode;
    private String principal;
    
    public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }
    
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    
    public Object getPrincipal() {
        return principal;
    }
    
    public Object getCredentials() {
        return authCode;
    }
    
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    
    public String getAuthCode() {
        return authCode;
    }
}
