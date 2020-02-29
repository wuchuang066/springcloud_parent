package com.vecher.qa.client;

import com.vecher.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname BaseClient
 * @Description TODO
 * @Date 2020/2/21 16:43
 * @Created by 74541
 */
// 名称不能是下划线
@FeignClient(value = "vecher-base",fallback = BaseClient.class)
public interface BaseClient {
    @RequestMapping(value="/label/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);
}
