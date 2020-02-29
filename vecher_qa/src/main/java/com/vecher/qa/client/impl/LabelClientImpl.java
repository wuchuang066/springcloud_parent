package com.vecher.qa.client.impl;

import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import com.vecher.qa.client.BaseClient;
import org.springframework.stereotype.Component;

/**
 * @description :
 **/
@Component
public class LabelClientImpl implements BaseClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR.getCode(), "熔断器启动了");
    }
}
