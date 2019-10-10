OAuth 2.0 是目前最流行的授权机制，用来授权第三方应用，获取用户数据。

### 授权码模式
授权码（authorization code）方式，指的是第三方应用先申请一个授权码，然后再用该码获取令牌。
以后每次获取数据的时候带上令牌就可以得到数据。

1. 访问页面，发现未登录，会跳到授权中心的登录页面：跳转时会带上参数，比如：
```
http://www.oauth.com/authorize?client_id=21&response_type=code&redirect_uri= http://www.crm.com/oauthLogin

client_id=21，client_id参数让 oauth 知道是谁在请求
response_type=code，表示要求返回授权码（code）
redirect_uri= http://www.crm.com /oauthLogin 表示oauth接受或拒绝请求后的跳转网址
scope=read表示要求的授权范围（这里是只读）。
```
2. 跳转到授权中心后，要求用户输入用户名和密码，点了登录并授权按钮后，这时 B 网站就会
跳回redirect_uri参数指定的网址。跳转时，会传回一个授权码，就像下面这样：
```
http://www.crm.com/oauthLogin?code=f3546620eda2e96a14ae530bc51c5dd8

上面的code参数就是授权码
```                                     
3. crm 网站拿到授权码以后，就可以在服务器端（java后台），向 授权中心 请求令牌。
    3.1 client_id参数和client_secret参数用来让 授权中心 确认crm 网站 的身份
    （client_secret参数是保密的，因此只能在服务器端（java后台）发请求）
    
    3.2 grant_type参数的值是AUTHORIZATION_CODE，表示采用的授权方式是授权码
    
    3.3 code参数是上一步拿到的授权码
    
    3.4 redirect_uri参数是令牌颁发后的回调网址
```java
Map<String,String> map=new HashMap<String, String>();
map.put("client_id","21");
map.put("client_secret","21");
map.put("grant_type","authorization_code");
map.put("code",code);
map.put("redirect_uri","http:// reim.medcrab.com/index");
//得到access token
String result= NetClient.getHttpPost("http://oauth.medcrab.com/",map);
```

4. 授权中心 收到请求以后，就会颁发令牌。具体做法是向redirect_uri指定的网址，发送一段JSON 数据，比如：
```
{    
  "access_token":"ACCESS_TOKEN",
  "token_type":"bearer",
  "expires_in":2592000,
  "refresh_token":"REFRESH_TOKEN",
  "scope":"read",
  "uid":100101,
  "info":{...}
}
上面 JSON 数据中，access_token字段就是令牌，crm 网站在服务器端（java后台）拿到的。
```
5. 接下来就可以带上令牌请求数据了，比如：
```java
String url= " http://www.oauth.com/user/getUser?access_token="+upt.getUsername();
String u= NetClient.httpGet(url); 
```

本文参考文档：
http://www.ruanyifeng.com/blog/2019/04/oauth-grant-types.html

http://www.ruanyifeng.com/blog/2019/04/oauth_design.html

https://www.cnblogs.com/cjsblog/p/9174797.html

代码来源：https://github.com/fangjiaxiaobai/oauth2.0