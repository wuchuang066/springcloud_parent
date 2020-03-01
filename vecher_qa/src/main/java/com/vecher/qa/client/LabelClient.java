package com.vecher.qa.client;

import com.vecher.entity.Result;
import com.vecher.qa.client.impl.LabelClientImpl;
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
@FeignClient(value = "vecher-base",fallback = LabelClientImpl.class)
public interface LabelClient {
    @RequestMapping(value="/label/{labelId}",method = RequestMethod.GET)
    Result findById(@PathVariable("labelId") String labelId);
}
