package com.vecher.user.controller;

import com.vecher.entity.PageResult;
import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import com.vecher.user.pojo.User;
import com.vecher.user.service.UserService;
import com.vecher.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginMap) {
        User user = userService.findByMobileAndPassword(loginMap.get("mobile"), loginMap.get("password"));

        if (user != null) {
			String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
            Map<String, String> map = new HashMap<>(3);
			map.put("token", token);
            map.put("name", user.getNickname());
            map.put("avatar", user.getAvatar());
            return new Result(true, StatusCode.OK.getCode(), "登录成功", map);
        } else {
            return new Result(true, StatusCode.LOGIN_ERROR.getCode(), "用户名或密码错误");
        }
    }

    /**
     * 增加粉丝数
     * @param userid
     * @param x
     */
    @RequestMapping(value = "/incfans/{userid}/{x}", method = RequestMethod.POST)
    public Result incFanscount(@PathVariable String userid, @PathVariable int x) {
        userService.incFanscount(userid, x);
        return new Result(true, StatusCode.OK.getCode(), "更新成功");
    }
    /**
     * 用户注册
     */
    @RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
    public Result register(@RequestBody User user, @PathVariable String code) {
        String checkcodeRedis = (String) redisTemplate.opsForValue().get("smscode_" + user.getMobile());
        if (checkcodeRedis == null || checkcodeRedis.isEmpty()) {
            return new Result(false, StatusCode.ERROR.getCode(), "请先获取手机验证码");
        }
        if (!code.equals(checkcodeRedis)) {
            return new Result(false, StatusCode.ERROR.getCode(), "验证码错误");
        }
        userService.add(user);
        return new Result(true, StatusCode.ERROR.getCode(), "注册成功");
    }

    /**
     * 发送短信验证码
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendsms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK.getCode(), "发送成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", userService.findById(id));
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK.getCode(), "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     * 目前这接口没用 在用户注册register 时候调用了service的方法
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK.getCode(), "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK.getCode(), "修改成功");
    }

    /**
     * 删除
     * 增加权限验证，必须拥有管理员权限，否则不能删除
     * 前后端约定，前端请求微服务时需要添加头信息Authorization，内容为Bearer+空格+token 防止token攻击
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        // 获取头信息
        Claims claims = (Claims) httpServletRequest.getAttribute("admin_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESS_ERROR.getCode(), "无权访问");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK.getCode(), "删除成功");
    }

}
