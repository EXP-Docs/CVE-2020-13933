package com.exp.cve;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	
    @Bean
    MyRealm myRealm(){
        return new MyRealm();
    }
    
    @Bean
    SecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());
        return manager;
    }
    
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorizedUrl");
        Map<String,String> map= new LinkedHashMap<String,String>();
        map.put("/doLogin/", "anon");
        map.put("/res/*", "authc");		// 权限配置： 当请求 /res/* 资源时，302 跳转到登陆页面进行身份认证
        bean.setFilterChainDefinitionMap(map);
        return bean;

    }
}
