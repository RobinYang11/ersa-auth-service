package com.ersa.authservice.dto;

import com.ersa.authservice.enums.LOGIN_TYPE;
import lombok.Data;

@Data
public class LoginDto {

    /**
     * 登录类型
     */
    private LOGIN_TYPE login_type;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码
     */
    private String phone;

    /**
     * 验证码
     */
    private int code;


}

