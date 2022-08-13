package com.ersa.authservice.utils;

import com.alibaba.fastjson.JSONObject;
import com.ersa.authservice.entity.UserBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class TokenUtil {

    /**
     * token有效期截止时间(365天)
     */
    public static final long APP_EXPIRE_AT_ON_MILLIS = 365 * 24 * 60 * 60 * 1000L;

    /**
     * token 密匙
     */
    public static final String APP_TOKEN_SECRET = "token unique encryption encoding use this to generate token";

    /**
     * JWT_ID
     */
    public static final String JWT_ID = UUID.randomUUID().toString();

    /**
     * 从token 中获取用户信息
     *
     * @param token
     * @return
     */
    public static UserBean getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(TokenUtil.generalKey())
                .parseClaimsJws(token).getBody();
        String sub = claims.getSubject();
        UserBean user = JSONObject.parseObject(sub, UserBean.class);
        return user;
    }


    public static String issueToken(UserBean user) {
        long presentTime = System.currentTimeMillis();
        Date issuedAtTime = new Date(presentTime);
        Date expirationTime = new Date(presentTime + APP_EXPIRE_AT_ON_MILLIS);

        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                .claim("uid", user.getId())
                .setSubject(JSONObject.toJSONString(user))
                .setIssuedAt(issuedAtTime)
                .setExpiration(expirationTime)
                .signWith(generalKey())
                .setId(JWT_ID)
                .compact();

        return token;
    }

    /**
     * @desc 验证token，通过返回true
     * @params [token]需要校验的串
     **/
    public static boolean verify(String token) {
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(TokenUtil.generalKey()).build()
                .parseClaimsJws(token)
                .getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 生成scretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(APP_TOKEN_SECRET);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
        return key;
    }

}
