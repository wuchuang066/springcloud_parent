package com.vecher.entity;

/**
 * @Classname StatusCode1
 * @Description TODO
 * @Date 2020/2/23 9:19
 * @Created by 74541
 */
public enum StatusCode {
    OK("成功",20000),ERROR("失败",20001),LOGIN_ERROR("登陆失败",20002),
    ACCESS_ERROR("认证失败",20003), REMOTE_ERROR("远程调用失败",20004), REP_ERROR("重复操作",20005);
    private String desc;//文字描述
    private Integer code; //对应的代码

    StatusCode(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
