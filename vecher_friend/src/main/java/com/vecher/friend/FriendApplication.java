package com.vecher.friend;

import com.vecher.util.IdWorker;
import com.vecher.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @description :
 **/
@SpringBootApplication
@EnableEurekaClient
//@EnableDiscoveryClient // 其他注册中心用
@EnableFeignClients // 远程调用使用feign添加的，调用其他模块方法
public class FriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
