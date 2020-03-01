package com.vecher.friend.controller;

import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import com.vecher.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description :
 **/
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 删除好友
     * @param friendid
     * @return
     */
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result remove(@PathVariable String friendid) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESS_ERROR.getCode(), "无权访问");
        }

        friendService.deleteFriend(claims.getId(), friendid);
        return new Result(true, StatusCode.OK.getCode(), "删除成功");
    }

    /**
     *  添加好友
     * @param friendid
     * @param type
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.GET)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        Claims claims = (Claims) request.getAttribute("user_claims");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESS_ERROR.getCode(), "无权访问");
        }

        if (type.equals("1")) {
            if (friendService.addFriend(claims.getId(), friendid) == 0) {
//                userClient.incFanscount();
                return new Result(false, StatusCode.REP_ERROR.getCode(), "已经添加此好友");
            }
        } else {
            // 不喜欢
            friendService.addNoFriend(claims.getId(), friendid);
        }
        return new Result(true, StatusCode.OK.getCode(), "操作成功");
    }
}
