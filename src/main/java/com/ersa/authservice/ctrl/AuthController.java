package com.ersa.authservice.ctrl;

import com.ersa.authservice.dto.LoginDto;
import com.ersa.authservice.dto.ResultDto;
import com.ersa.authservice.entity.UserBean;
import com.ersa.authservice.service.UserService;
import com.ersa.authservice.utils.BCryptUtil;
import com.ersa.authservice.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {


    @Autowired
    private UserService userService;

//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }

    @PostMapping("/api/loginWithPwd")
    ResultDto loginWithPwd(@RequestBody LoginDto loginDto){

        ResultDto resultDto = new ResultDto();

        //校验参数
        if(loginDto.getPhone() ==null && null == loginDto.getPassword()){
            throw new IllegalArgumentException("手机号密码是必须传!");
        }

        UserBean user =this.userService.queryUserByPhone(loginDto.getPhone());

        if(null != user) {
            boolean isEqual =BCryptUtil.checkpw(loginDto.getPassword(),user.getPassword());
            if(isEqual) {
                try {
                    String token = TokenUtil.issueToken(user.getId(), user.toString());
                    resultDto.setResult(token);
                    resultDto.setError("登录成功");
                    return resultDto;
                } catch (Exception e) {
                    log.error(e.toString());
                    throw new RuntimeException(e);
                }
            } else {
                resultDto.setInfo("密码错误");
                resultDto.setResult(false);
                return resultDto;
            }
        } else{
            resultDto.setInfo("没有该用户");
            resultDto.setResult(null);
            return resultDto;
        }
    }

    @PostMapping("/api/loginWithSms")
    ResultDto loginWithSms(@RequestBody LoginDto loginDto){
        return null;
    }

    @PostMapping("/api/loginWithWchat")
    ResultDto loginWithWchat(@RequestBody LoginDto loginDto){
        return null;
    }

    @PostMapping("/api/loginWithSpark")
    ResultDto loginWithSpark(@RequestBody LoginDto loginDto){
        return null;
    }

}
