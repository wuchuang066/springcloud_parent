package com.vecher.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @description : 安全配置类
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 添加security 依赖之后默认会拦截 所有请求路径，但是我们只是需要密码加密，所以配置将所有路径都放行
        // authorizeRequests 所有security 全注解配置实现的开端，表示开始说明需要的权限
        // 需要的权限分两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限
        // antMatchers 表示拦截什么路径 permitAll 任何权限都可以访问，直接放行所有
        // anyRequest 任何的请求 authenticated 认证后才能访问
        // .and().csrf().disable(); 固定写法，表示使csrf拦截失效
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
