package com.vecher.base.controller;

import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname BaseExceptionHandler
 * @Description 全局捕获异常
 *  * 原理:使用AOP切面技术
 * @Date 2020/2/15 19:10
 * @Created by 74541
 */
@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR.getCode(),e.getMessage());
    }
}
