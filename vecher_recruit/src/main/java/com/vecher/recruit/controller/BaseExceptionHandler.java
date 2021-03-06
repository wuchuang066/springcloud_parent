package com.vecher.recruit.controller;
import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e){
        e.printStackTrace();        
        return new Result(false, StatusCode.ERROR.getCode(), "执行出错");
    }
}
