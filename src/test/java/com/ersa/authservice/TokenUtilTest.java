package com.ersa.authservice;

import com.alibaba.fastjson.JSONObject;
import com.ersa.authservice.entity.UserBean;
import com.ersa.authservice.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class TokenUtilTest {

    /**
     * 测试颁发token
     */
    @Test
    void testIssueToken() {
        UserBean user = new UserBean();

        String uid = String.valueOf(UUID.randomUUID());
        String name = "robin";
        String email = "7777@qq.com";
        String phone = "18888888888";
        String esType = "esType";

        user.setId(uid);
        user.setName(name);
        user.setEs_type(esType);
        user.setEmail(email);
        user.setPhone(phone);

        String token = TokenUtil.issueToken(user);
        Claims claims = (Claims) Jwts
                .parserBuilder()
                .setSigningKey(TokenUtil.generalKey()).build()
                .parseClaimsJws(token)
                .getBody();

        String sub = claims.getSubject();
        UserBean parsedUser = JSONObject.parseObject(sub).toJavaObject(UserBean.class);

        Assertions.assertEquals(name, parsedUser.getName());
        Assertions.assertEquals(uid, parsedUser.getId());
        Assertions.assertEquals(email, parsedUser.getEmail());
        Assertions.assertEquals(phone, parsedUser.getPhone());
        Assertions.assertEquals(esType, parsedUser.getEs_type());
    }
}
