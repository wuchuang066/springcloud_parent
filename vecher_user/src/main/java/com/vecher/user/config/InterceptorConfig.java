package com.vecher.user.config;

import com.vecher.user.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import com.vecher.util.JwtUtil;

/**
 *
 * @description : filter config class
 **/
@Configuration
public class InterceptorConfig {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login"); // 不拦截的登录地址
    }
}
