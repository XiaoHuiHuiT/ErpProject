package com.qc.system.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.qc.system.realm.UserRealm;
import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro的自动配置类
 *
 * @author LJH
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "shiro")
public class ShiroAutoConfiguration {

    private String hashAlgorithmName = "md5";// 加密方式
    private int hashIterations = 2;// 散列次数
    private String targetBeanName = "shiroFilter";
    private boolean targetFilterLifecycle = true;
    private String loginUrl = "/index.html";// 未登陆跳转
    private String unauthorizedUrl = "/unauthorized.html";// 未授权跳转


    private String[] anonUrls;
    private String[] authcUrls;



    /**
     * 声明凭证匹配器
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 注入加密方式
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        // 注入散列次数
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }

    /**
     * 声明userRealm
     */
    @Bean
    public UserRealm userRealm(HashedCredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        // 注入凭证匹配器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    /**
     * 声明安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 配置shiro的代理过滤器
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBeanDelegatingFilterProxy() {
        // 创建注册器
        FilterRegistrationBean<DelegatingFilterProxy> registrationBean = new FilterRegistrationBean<>();
        // 创建过滤器
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        // 设置过滤器的参数
        proxy.setTargetBeanName(targetBeanName);
        proxy.setTargetFilterLifecycle(targetFilterLifecycle);
        // 注册
        registrationBean.setFilter(proxy);
        Collection<String> servletNames = new ArrayList<>();
        servletNames.add(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
        registrationBean.setServletNames(servletNames);
        return registrationBean;
    }

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean(value = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 注入安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 设置非登陆跳转的页面
        factoryBean.setLoginUrl(loginUrl);
        // 未授权的跳转页
        factoryBean.setUnauthorizedUrl(unauthorizedUrl);

        Map<String, String> filterChainDefinitionMap = new HashMap<>();

        //设置放行的url
        if(anonUrls!=null&&anonUrls.length>0) {
            for (String url : anonUrls) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }
        //设置拦击的url
        if(authcUrls!=null&&authcUrls.length>0) {
            for (String url : authcUrls) {
                filterChainDefinitionMap.put(url, "authc");
            }
        }
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    // 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    /****加入对注解的支持******/
    /**
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
