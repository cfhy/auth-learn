package com.fxb.client.filter;

import com.fxb.client.token.OAuth2Token;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author fangxiaobai on 2017/10/13 17:17.
 * @description OAuth2 自定义  认证过滤器。
 */
public class OAuth2AuthenticationFilter extends AuthenticatingFilter {
    
    // oauth2 authc code 参数名
    private String authCodeParam = "code";
    
    // 客户端id
    private String clientId;
    
    //服务器端登录成功/失败后重定向到的客户端地址。
    private String redirectUrl;
    
    // oauth2 服务器响应类型
    private String responseType = "code";
    
    private String failureUrl;
    
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String code = httpServletRequest.getParameter("code");
        return new OAuth2Token(code);
    }
    
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        
        if(!StringUtils.isEmpty(error)) {
            WebUtils.issueRedirect(request, response, failureUrl + "?error=" + error + "error_description=" + errorDescription);
            return false;
        }
        
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated()) {
            if(StringUtils.isEmpty(request.getParameter(authCodeParam))) {
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }
        
        
        return executeLogin(request, response);
    }
    
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        return false;
    }
    
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        Subject subject = getSubject(request, response);
        if(subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public String getAuthCodeParam() {
        return authCodeParam;
    }
    
    public void setAuthCodeParam(String authCodeParam) {
        this.authCodeParam = authCodeParam;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getRedirectUrl() {
        return redirectUrl;
    }
    
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    
    public String getResponseType() {
        return responseType;
    }
    
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
    
    public String getFailureUrl() {
        return failureUrl;
    }
    
    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }
}
