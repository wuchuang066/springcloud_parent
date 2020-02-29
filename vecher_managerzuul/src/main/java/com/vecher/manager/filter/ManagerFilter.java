package com.vecher.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.vecher.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @description :
 **/
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     *   post  之后过滤  pre 之前过滤
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
        //
    }

    /**
     * 多个过滤器的执行顺序 数字越小 越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     *  过滤器是否开启  true 开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 执行具体内容 任何Object 值都表示继续执行
     * setSendZuulResponse(false) 表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("Zuul过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }
        String url = request.getRequestURI();
        if (url.indexOf("/admin/login") > 0) {
            System.out.println("登录界面" + url);
            return null;
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null) {
                if ("admin".equals(claims.get("roles"))) {
                    requestContext.addZuulRequestHeader("Authorization", authHeader);
                    System.out.println("token 验证通过， 添加了头信息" + authHeader);
                    return null;
                }
            }
        }

        // 终止运行，过滤请求，不对其进行路由
        requestContext.setSendZuulResponse(false);
        // http状态码，返回错误码
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
