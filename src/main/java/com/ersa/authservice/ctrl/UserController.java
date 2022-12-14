package com.ersa.authservice.ctrl;

import com.ersa.authservice.dto.ResultDto;
import com.ersa.authservice.entity.UserBean;
import com.ersa.authservice.service.UserService;
import com.ersa.authservice.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/createUser")
    ResultDto create(@RequestBody UserBean  user){
        ResultDto resultDto = new ResultDto();
        try{
           UserBean existUser=  userService.queryUserByPhone(user.getPhone());
           if(null != existUser){
             throw new  IllegalArgumentException("手机号已注册");
           }
            this.userService.createUser(user) ;
            resultDto.setResult(user);
            return resultDto;
        } catch (Exception e){
            log.error(e.toString());
            resultDto.setError(e.toString());
            return resultDto;
        }
    }

    @GetMapping("/api/queryUserById")
    ResultDto create(String id){
        ResultDto resultDto = new ResultDto();
        try{
            UserBean user = this.userService.queryUserById(id);
            // 隐藏密码
            user.setPassword(null);
            resultDto.setResult(user);
            return resultDto;
        } catch (Exception e){
            log.error(e.toString());
            resultDto.setError(e.toString());
            return resultDto;
        }
    }

    @GetMapping("/api/currentUser")
    ResultDto currentUser(String token){

        if(null == token){
            log.error("未传token！");
            throw  new IllegalArgumentException("未传token参数!");
        }

        ResultDto resultDto = new ResultDto();
        try{
            UserBean user = TokenUtil.getUserFromToken(token);
            //隐藏密码
            user.setPassword(null);
            resultDto.setResult(user);
            return resultDto;
        } catch (Exception e){
            log.error(e.toString());
            resultDto.setError(e.toString());
            return resultDto;
        }
    }


}

