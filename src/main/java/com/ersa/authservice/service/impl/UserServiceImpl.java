package com.ersa.authservice.service.impl;

import com.ersa.authservice.dto.UserDto;
import com.ersa.authservice.entity.UserBean;
import com.ersa.authservice.service.UserService;
import com.ersa.authservice.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate ;

    @Override
    public UserBean createUser(UserBean user) {

        // 密码加密
        if(null != user.getPassword()){
            String pwd= BCryptUtil.hashpw(user.getPassword(),BCryptUtil.gensalt());
            user.setPassword(pwd);
        }
        // 设置注册时间
        user.setTmCreate(System.currentTimeMillis());
        return this.mongoTemplate.save(user);
    }

    @Override
    public UserBean queryUserById(String id) {
        return this.mongoTemplate.findById(id,UserBean.class);
    }

    @Override
    public UserBean queryUserByPhone(String phone) {
        Query query = new Query();
        query.addCriteria(Criteria.where("phone").is(phone));
        return this.mongoTemplate.findOne(query,UserBean.class);
    }

    @Override
    public boolean updateUser(UserDto user) {
        return false;
    }
}
