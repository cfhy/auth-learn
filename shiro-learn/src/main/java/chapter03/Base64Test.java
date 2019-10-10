package chapter03;

import org.apache.shiro.codec.Base64;

/**
 *  base64 编码/解码操作
 */
public class Base64Test {
    public static void main(String[] args) {
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
       System.out.println(base64Encoded);
        System.out.println(str2);
    }
}
