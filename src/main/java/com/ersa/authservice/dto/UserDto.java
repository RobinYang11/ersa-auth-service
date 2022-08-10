package com.ersa.authservice.dto;


import com.ersa.authservice.entity.UserBean;
import lombok.Data;

@Data
public class UserDto extends UserBean {

    private int page =1;

    private int pageSize =10;

    private int total;

    private boolean hasNext;

}

