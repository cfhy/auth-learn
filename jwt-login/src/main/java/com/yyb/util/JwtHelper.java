
package com.yyb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Helon
 * @Description: JWT工具类
 * 参考官网：https://jwt.io/
 * 实现机制参考博客：https://www.cnblogs.com/tyrion1990/p/8134384.html
 * JWT的数据结构为：A.B.C三部分数据，由字符点"."分割成三部分数据
 * A-header头信息
 * B-payload 有效负荷 一般包括：已注册信息（registered claims），公开数据(public claims)，私有数据(private claims)
 * C-signature 签名信息 是将header和payload进行加密生成的
 * @Data: Created in 2018/7/19 14:11
 * @Modified By:
 */
public class JwtHelper {
    private static Logger logger = LoggerFactory.getLogger(JwtHelper.class);
    private static final String SIGN_KEY="3d990d2276917dfac04467df11fff26d";
    //5分钟后过期
    private static final long EXPIRE_TIME=5*60*60*1000L;

    /**
     * @Author: Helon
     * @Description: 生成JWT字符串
     * 格式：A.B.C
     * A-header头信息
     * B-payload 有效负荷
     * C-signature 签名信息 是将header和payload进行加密生成的
     * @param userId - 用户编号
     * @param userName - 用户名
     * @Data: 2018/7/28 19:26
     * @Modified By:
     */
    public static String generateJWT(String userId, String userName) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将SIGN_KEY常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SIGN_KEY);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = Jwts.builder().setHeader(headMap)
                //加密后的客户编号
                .claim("userId",userId )
                //客户名称
                .claim("userName", userName)
                //Signature
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        long expMillis = nowTimeMillis + EXPIRE_TIME;
        Date expDate = new Date(expMillis);
        builder.setExpiration(expDate).setNotBefore(now);
        return builder.compact();
    }

    /**
     * @Author: Helon
     * @Description: 解析JWT
     * 返回Claims对象
     * @param jsonWebToken - JWT
     * @Data: 2018/7/28 19:25
     * @Modified By:
     */
    public static Claims parseJWT(String jsonWebToken) {
        Claims claims = null;
        if (!StringUtils.isEmpty(jsonWebToken)) {
            //解析jwt
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SIGN_KEY))
                    .parseClaimsJws(jsonWebToken).getBody();
        }else {
            logger.warn("[JWTHelper]-json web token 为空");
        }
        return claims;
    }
}