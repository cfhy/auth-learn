package chapter03;

import org.apache.shiro.crypto.hash.Sha256Hash;

public class Sha256HashTest {
    public static void main(String[] args) {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha256Hash(str, salt).toString();

        System.out.println(sha1);
    }
}
