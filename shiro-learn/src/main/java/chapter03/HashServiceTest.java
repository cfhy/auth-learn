package chapter03;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * 1、首先创建一个 DefaultHashService，默认使用 SHA-512 算法；
 * 2、可以通过 hashAlgorithmName 属性修改算法；
 * 3、可以通过 privateSalt 设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
 * 4、可以通过 generatePublicSalt 属性在用户没有传入公盐的情况下是否生成公盐；
 * 5、可以设置 randomNumberGenerator 用于生成公盐；
 * 6、可以设置 hashIterations 属性来修改默认加密迭代次数；
 * 7、需要构建一个 HashRequest，传入算法、数据、公盐、迭代次数。
 */
public class HashServiceTest {
    public static void main(String[] args) {
        DefaultHashService hashService = new DefaultHashService(); //默认算法 SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认 false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个

        hashService.setHashIterations(1); //生成 Hash 值的迭代次数
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();

        //SecureRandomNumberGenerator 用于生成一个随机数：
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        hex = randomNumberGenerator.nextBytes().toHex();
    }
}
