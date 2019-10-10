package chapter03;

import org.apache.shiro.codec.Hex;
import org.junit.Assert;

/**
 *  16 进制字符串编码/解码操作
 */
public class Hex16Test {
    public static void main(String[] args) {
        String str = "hello";
        String base64Encoded = Hex.encodeToString(str.getBytes());
        String str2 = new String(Hex.decode(base64Encoded.getBytes()));
        Assert.assertEquals(str, str2);
    }
}
