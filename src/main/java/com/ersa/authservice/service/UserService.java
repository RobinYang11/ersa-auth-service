package com.ersa.authservice.service;

import com.ersa.authservice.entity.UserBean;

public interface UserService {

    UserBean createUser(UserBean user);

    UserBean queryUserById(String id);

    boolean updateUser(UserBean user);

}
