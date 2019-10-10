package com.fxb.oauth2.controller;

import com.fxb.oauth2.services.AuthorizeService;
import com.fxb.oauth2.services.ClientService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author fangxiaobai on 2017/10/13 15:47.
 * @description
 */

@Controller
@RequestMapping("/server/")
public class AuthorizeController {
    
    @Autowired
    private AuthorizeService authorizeService;
    
    @Autowired
    private ClientService clientService;
    
    @RequestMapping("authorize")
    public Object authorize(Model model, HttpServletRequest request) throws OAuthSystemException, URISyntaxException {
        
        //构建OAuth请求
        OAuthAuthzRequest oAuthAuthzRequest = null;
        try {
            oAuthAuthzRequest = new OAuthAuthzRequest(request);
            
            // 根据传入的clientId 判断 客户端是否存在
            if(!authorizeService.checkClientId(oAuthAuthzRequest.getClientId())) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription("客户端验证失败，如错误的client_id/client_secret")
                        .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
            
            // 判断用户是否登录
            Subject subject = SecurityUtils.getSubject();
            
            if(!subject.isAuthenticated()) {
                if(!login(subject, request)) {
                    model.addAttribute("client", clientService.findByClientId(oAuthAuthzRequest.getClientId()));
                    return "oauth2login";
                }
            }
            String username = (String) subject.getPrincipal();
            
            //生成授权码
            String authorizationCode = null;
         
            String responseType = oAuthAuthzRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if(responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oAuthIssuer.authorizationCode();
                authorizeService.addAuthCode(authorizationCode, username);
            }
            
            // 进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            // 设置授权码
            builder.setCode(authorizationCode);
            // 根据客户端重定向地址
            String redirectURI = oAuthAuthzRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            // 构建响应
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            
            // 根据OAuthResponse 返回 ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch(OAuthProblemException e) {
            // 出错处理
            String redirectUri = e.getRedirectUri();
            if(OAuthUtils.isEmpty(redirectUri)) {
                return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
            }
            // 返回错误消息
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }
    }
    
    private boolean login(Subject subject, HttpServletRequest request) {
        if("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }
        
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        
        try {
            subject.login(token);
            return true;
        }catch(Exception e){
            request.setAttribute("error","登录失败"+e.getClass().getName());
            return false;
        }
    }
    
}
