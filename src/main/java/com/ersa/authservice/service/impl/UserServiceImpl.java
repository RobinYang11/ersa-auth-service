package com.ersa.authservice.service.impl;

import com.ersa.authservice.dto.UserDto;
import com.ersa.authservice.entity.UserBean;
import com.ersa.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate ;

    @Override
    public UserBean createUser(UserBean user) {
        user.setTmCreate(System.currentTimeMillis());
        return this.mongoTemplate.save(user);
    }

    @Override
    public UserBean queryUserById(String id) {
        return null;
    }

    @Override
    public boolean updateUser(UserDto user) {
        return false;
    }
}
