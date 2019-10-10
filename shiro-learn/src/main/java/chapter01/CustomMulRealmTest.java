package chapter01;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * securityManager 会按照 realms 指定的顺序进行身份认证。此处我们使用显示指定顺序的方
 * 式指定了 Realm 的顺序，如果删除“securityManager.realms=$myRealm1,$myRealm2”，那
 * 么 securityManager 会按照 realm 声明的顺序进行使用（即无需设置 realms 属性，其会自动
 * 发 现 ） ， 当 我 们 显 示 指 定 realm 后 ， 其 他 没 有 指 定 realm 将 被 忽 略 ， 如
 * “securityManager.realms=$myRealm1”，那么 myRealm2 不会被自动设置进去。
 */
public class CustomMulRealmTest {
    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:chapter01/custom-mul-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
        }
        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
    }
}
