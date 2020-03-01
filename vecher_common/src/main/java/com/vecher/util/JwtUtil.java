package com.vecher.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description :
 **/
@Getter
@Setter
@Component
@ConfigurationProperties("jwt.config")  // 读取调用模块里面的 jwt.config 属性的key 和ttl配置信息
public class JwtUtil {
    // 加的盐
    private String key;

    // 过期时间 多长时间过期
    private long ttl;

    /**
     * 创建token 信息 并在里面添加角色信息
     * @param id 用户登录的id
     * @param subobject 用户名
     * @param roles 角色信息
     * @return
     */
    public String createJWT(String id, String subobject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subobject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key)
                .claim("roles", roles);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析token信息方法
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
