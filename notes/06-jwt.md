JSON Web Token（JWT）是一个非常轻巧的规范。这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息。

JWT是一个含签名并携带用户相关信息的加密串，页面请求校验登录接口时，请求头中携带JWT串到后端服务，后端通
过签名加密串匹配校验，保证信息未被篡改。校验通过则认为是可靠的请求，将正常返回数据。

### 什么情况下使用JWT比较适合？
- 授权：这是最常见的使用场景，解决单点登录问题。因为JWT使用起来轻便，开销小，服务端不用记录用户状态信
息（无状态），所以使用比较广泛；
- 信息交换：JWT是在各个服务之间安全传输信息的好方法。因为JWT可以签名，例如，使用公钥/私钥对儿 - 可以确
定请求方是合法的。此外，由于使用标头和有效负载计算签名，还可以验证内容是否未被篡改。

### JWT的组成
一个JWT实际上就是一个字符串，它由三部分组成，头部、载荷与签名，中间以（.）分隔。

1. header（头信息）
header由两部分组成，令牌类型（即：JWT）、散列算法（HMAC、RSASSA、RSASSA-PSS等），例如：
```
{
  "alg": "HS256",
  "typ": "JWT"
}

```
然后，这个JSON被编码为Base64Url，形成JWT的第一部分。

2. Payload（有效载荷）
JWT的第二部分是payload，其中包含claims。claims是关于实体（常用的是用户信息）和其他数据的声明，

claims有三种类型： registered, public, private claims。
- Registered claims： 这些是一组预定义的claims，非强制性的，但是推荐使用， 
    iss（发行人）， exp（到期时间）， sub（主题）， aud（观众）等；
- Public claims: 自定义claims，注意不要和JWT注册表中属性冲突，[这里可以查看JWT注册表](https://www.iana.org/assignments/jwt/jwt.xhtml)
- Private claims: 这些是自定义的claims，用于在同意使用这些claims的各方之间共享信息，它们既不是Registered claims，也不是Public claims。

以下是payload示例：
```
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```
然后，再经过Base64Url编码，形成JWT的第二部分；

注意，不要在JWT的payload或header中放置敏感信息，除非它们是加密的。

3. Signature（签名）
要创建签名部分，必须采用编码的Header，编码的Payload，秘钥，Header中指定的算法，并对其进行签名。
例如，如果要使用HMAC SHA256算法，将按以下方式创建签名：
```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```
签名是用于验证消息在传递过程中有没有被更改，并且，对于使用私钥签名的token，它还可以验证
JWT的发送方是否为它所称的发送方。

### JWT工作机制
在身份验证中，当用户使用其凭据成功登录时，将返回JSON Web Token。由于令牌是凭证，
因此必须非常小心以防止出现安全问题。一般情况下，不应将令牌保留的时间超过要求。理论上超时时间越短越好。

每当用户想要访问受保护的路由或资源时，用户代理应该使用Bearer模式发送JWT，通常在
Authorization header中。标题内容应如下所示：
```
Authorization: Bearer <token>
```
在某些情况下，这可以作为无状态授权机制。服务器的受保护路由将检查Authorization header中的有效JWT ，
如果有效，则允许用户访问受保护资源。如果JWT包含必要的数据，则可以减少查询数据库或缓存信息。

如果在Authorization header中发送令牌，则跨域资源共享（CORS）将不会成为问题，因为它不使用cookie。

注意：使用签名令牌，虽然他们无法更改，但是令牌中包含的所有信息都会向用户或其他方公开。这意味着
不应该在令牌中放置敏感信息。

### 使用JWT的好处是什么？
相比Simple Web Tokens (SWT)（简单Web令牌） and Security Assertion Markup Language Tokens (SAML)（安全断言标记语言令牌）；

- JWT比SAML更简洁，在HTML和HTTP环境中传递更方便；
- 在安全方面，SWT只能使用HMAC算法通过共享密钥对称签名。但是，JWT和SAML令牌可以使用X.509证书形式的
公钥/私钥对进行签名。与签名JSON的简单性相比，使用XML数字签名可能会存在安全漏洞；
- JSON解析成对象相比XML更流行、方便。

### JWT与Session的差异
相同点是，它们都是存储用户信息；然而，Session是在服务器端的，而JWT是在客户端的。

Session方式存储用户信息的最大问题在于要占用大量服务器内存，增加服务器的开销。

而JWT方式将用户状态分散到了客户端中，可以明显减轻服务端的内存压力。

Session的状态是存储在服务器端，客户端只有session id；而Token的状态是存储在客户端。

### JWT与OAuth的区别
相同点是，它们都是存储用户信息；然而，Session是在服务器端的，而JWT是在客户端的。
    
Session方式存储用户信息的最大问题在于要占用大量服务器内存，增加服务器的开销。

而JWT方式将用户状态分散到了客户端中，可以明显减轻服务端的内存压力。

Session的状态是存储在服务器端，客户端只有session id；而Token的状态是存储在客户端。





















