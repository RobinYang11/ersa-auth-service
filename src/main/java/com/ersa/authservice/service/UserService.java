package com.ersa.authservice.service;

import com.ersa.authservice.dto.UserDto;
import com.ersa.authservice.entity.UserBean;

public interface UserService {

    UserBean createUser(UserBean user);

    UserBean queryUserById(String id);

    UserBean queryUserByPhone(String phone);

    boolean updateUser(UserDto user);

}
