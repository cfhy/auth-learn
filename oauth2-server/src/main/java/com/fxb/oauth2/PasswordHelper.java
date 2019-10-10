package com.fxb.oauth2;

import com.fxb.oauth2.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author fangxiaobai on 2017/10/13 21:02.
 * @description
 *
 *      用户密码 加密工具类
 *
 */
@Service
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    
    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 2;
    
    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }
    
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
    
    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }
    
    public void encryptPassword(User user) {
        
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialSalt()),
                hashIterations).toHex();
        
        user.setPassword(newPassword);
    }
}
