package com.ersa.authservice.service;

import com.ersa.authservice.entity.UserBean;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements  UserService {
    @Override
    public UserBean createUser(UserBean user) {
        return null;
    }

    @Override
    public UserBean queryUserById(String id) {
        return null;
    }

    @Override
    public boolean updateUser(UserBean user) {
        return false;
    }
}
