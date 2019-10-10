package chapter02;

import org.junit.Assert;

public class TestIsPermitted extends BaseTest{
    public static void main(String[] args) {
        new TestIsPermitted().test();
    }
    public  void test(){
        login("classpath:chapter02/shiro-permission.ini", "zhang", "123");
        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user:create"));
        //判断拥有权限：user:update and user:delete
        Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));
        //判断没有权限：user:view
        Assert.assertFalse(subject().isPermitted("user:view"));

        //断言拥有权限：user:create
        subject().checkPermission("user:create");
        //断言拥有权限：user:delete and user:update
        subject().checkPermissions("user:delete", "user:update");
        //断言拥有权限：user:view 失败抛出异常
        //subject().checkPermissions("user:view");
        //判断是否拥有权限：order:view
        subject().checkPermissions("order:view");
        subject().checkPermissions("order:*");
    }
}
