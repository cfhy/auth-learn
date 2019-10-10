package chapter03;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5Test {
    public static void main(String[] args) {
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()

         md5 = new Md5Hash(str, salt,2).toString();
    }
}
