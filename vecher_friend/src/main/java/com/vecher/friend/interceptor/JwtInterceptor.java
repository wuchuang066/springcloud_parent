package com.vecher.friend.interceptor;

import com.vecher.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description : 拦截器 拦截用户功能
 **/
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("经过了拦截器");
        // 每次访问都需要进行
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.parseJWT(token);
                String roles = (String) claims.get("roles");
                if (roles != null) {
                    if ("admin".equals(roles)) {
                        request.setAttribute("admin_claims", claims);
                    }
                    if ("user".equals(roles)) {
                        request.setAttribute("user_claims", claims);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("token不正确！");
            }
        }
        return true;
    }
}
