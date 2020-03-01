package com.vecher.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description : Interface for
 **/
@FeignClient("vecher-user")
public interface UserClient {

    /**
     * 增加粉丝数
     * @param userid
     * @param x
     */
    @RequestMapping(value = "/user/incfans/{userid}/{x}", method = RequestMethod.POST)
    void incFanscount(@PathVariable("userid") String userid, @PathVariable("x") int x);

    @RequestMapping(value = "/user/incfollow/{userid}/{x}", method = RequestMethod.POST)
    void incFollowcount(@PathVariable("userid") String userid, @PathVariable("x") int x);
}
