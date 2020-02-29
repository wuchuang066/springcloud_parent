package com.vecher.spit.controller;

import com.vecher.entity.PageResult;
import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import com.vecher.spit.pojo.Spit;
import com.vecher.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @description :
 **/
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", spitService.findAll());
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", spitService.findById(spitId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK.getCode(), "保存成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK.getCode(), "修改成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK.getCode(), "删除成功");
    }
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.findByParentId(parentid, page, size);
        return new Result(true, StatusCode.OK.getCode(), "查询成功",
                new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId) {
        // 解决重复点赞问题
        String userid = "111";  // 从登陆信息中获取
        if (redisTemplate.opsForValue().get("thumbup_" + userid) != null) {
            return new Result(false, StatusCode.REP_ERROR.getCode(), "不能重复点赞");
        }

        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_" + userid, 1);
        return new Result(true, StatusCode.OK.getCode(), "点赞成功");
    }

}
