package com.fxb.oauth2.realm;

import com.fxb.oauth2.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fangxiaobai on 2017/10/13 17:59.
 * @description
 *
 *          自定义 User Realm  进行认证和授权
 */
public class UserRealm extends AuthorizingRealm{
    
    @Autowired
    private com.fxb.oauth2.services.UserService userService;
    
    
    
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String username = (String) principals.getPrimaryPrincipal();
        return new SimpleAuthorizationInfo();
    }
    
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.findByUsername(username);
    
        if(null==user) {
            throw new UnknownAccountException();
        }
    
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialSalt()),
                getName()
        );
        return authenticationInfo;
    }
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
    
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }
    
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
    
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }
    
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }
    
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
