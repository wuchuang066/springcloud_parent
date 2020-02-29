package com.vecher.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回数据的封装实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private boolean flag;
    private Integer code;
    private String message;
    // 返回的数据
    private Object data;

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
