//package com.qc.system.config;
//
//import com.alibaba.druid.support.http.WebStatFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.HashMap;
//
//@Configuration
//public class DruidConfig {
//
//    // 配置Filter
//    @Bean
//    public FilterRegistrationBean webStatFilter(){
//        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
//
//        HashMap<Object, Object> hashMap = new HashMap<>();
//        hashMap.put("exclusions", "*.js,*.css,/druid/*");
//        bean.setInitParameters(hashMap);
//        bean.setUrlPatterns(Arrays.asList("/*"));
//        return bean;
//    }
//
//}
