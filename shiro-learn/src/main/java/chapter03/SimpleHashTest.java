package chapter03;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class SimpleHashTest {
    public static void main(String[] args) {
        String str = "hello";
        String salt = "123";
        //内部使用 MessageDigest
        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
         simpleHash = new SimpleHash("MD5", str, salt).toString();
         System.out.println(simpleHash);

        String md5 = new Md5Hash(str, salt).toString();
        System.out.println(md5);
        System.out.println(simpleHash.equals(md5));
    }
}
