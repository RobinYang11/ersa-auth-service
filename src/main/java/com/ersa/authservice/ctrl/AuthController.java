package com.ersa.authservice.ctrl;

import com.ersa.authservice.dto.LoginDto;
import com.ersa.authservice.dto.ResultDto;
import com.ersa.authservice.entity.UserBean;
import com.ersa.authservice.service.UserService;
import com.ersa.authservice.utils.BCryptUtil;
import com.ersa.authservice.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    private long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;

    @PostMapping("/api/loginWithPwd")
    ResultDto loginWithPwd(@RequestBody LoginDto loginDto) {

        ResultDto resultDto = new ResultDto();

        //校验参数
        if (loginDto.getPhone() == null && null == loginDto.getPassword()) {
            throw new IllegalArgumentException("手机号密码是必须传!");
        }

        UserBean user = this.userService.queryUserByPhone(loginDto.getPhone());

        if (null != user) {
            boolean isEqual = BCryptUtil.checkpw(loginDto.getPassword(), user.getPassword());
            if (isEqual) {
                try {
                    String token = TokenUtil.issueToken(user);

                    redisTemplate.opsForValue().set(token, user.toString());
                    redisTemplate.expire(token, TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

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
        } else {
            resultDto.setInfo("没有该用户");
            resultDto.setResult(null);
            return resultDto;
        }
    }


    @PostMapping("/api/verifyToken")
    ResultDto verify(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token 必传");
        }
        boolean result = TokenUtil.verify(token);
        return ResultDto
                .build()
                .setResult(result);
    }

    @PostMapping("/api/loginWithSms")
    ResultDto loginWithSms(@RequestBody LoginDto loginDto) {
        return null;
    }

    @PostMapping("/api/loginWithWchat")
    ResultDto loginWithWchat(@RequestBody LoginDto loginDto) {
        return null;
    }

    @PostMapping("/api/loginWithSpark")
    ResultDto loginWithSpark(@RequestBody LoginDto loginDto) {
        return null;
    }

}
