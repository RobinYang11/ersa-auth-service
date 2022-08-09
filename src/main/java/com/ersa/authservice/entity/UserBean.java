package com.ersa.authservice.entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
public class UserBean {

    private  String id;

    /**
     * 昵称
     */
    private  String name;

    /**
     * 密码
      */
    private String password;

    /**
     * 位置
     */
    private String location;

    /**
     * 年龄
     */
    private int age;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 自我简介
     */
    private String brief;

    /**
     * 性别   1
     */
    private int sex;

    /**
     * 用户状态 1 正常 2 封禁 3 注销
     */
    private int status;

    /**
     * es索引类型
     */
    private String es_type;
}
