package com.vecher.qa.client.impl;

import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import com.vecher.qa.client.LabelClient;
import org.springframework.stereotype.Component;

/**
 * @description :
 **/
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR.getCode(), "熔断器启动了");
    }
}
