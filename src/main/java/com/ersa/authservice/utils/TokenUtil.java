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
     * Web端token最大有效时长，单位：秒
     */
    public static final int WEB_MAX_AGE_ON_SECOND = 7200;
    /**
     * App端token有效期截止时间(365天)
     */
    public static final long APP_EXPIRE_AT_ON_MILLIS = 365 * 24 * 60 * 60 * 1000L;

    public static final String APP_TOKEN_SECRET = "token unique encryption encoding use this to generate token";
    private static final String WEB_TOKEN_SECRET = "token";

    /**
     * JWT_ID
     */
    public static final String JWT_ID = UUID.randomUUID().toString();

    /**
     *  从token 中获取用户信息
     * @param token
     * @return
     */
   public static UserBean getUserFromToken(String token) {
       Claims claims = Jwts.parser()
               .setSigningKey(TokenUtil.generalKey())
               .parseClaimsJws(token).getBody();
       String sub = claims.getSubject();
       UserBean user = JSONObject.parseObject(sub,UserBean.class);
       return user;
   }

    /**
     * 生成JWT token
     *
     * @param userId
     * @return
     */
    public static String issueToken(String userId, String subject) throws Exception {
        try {
            //设置过期时间
            long presentTime = System.currentTimeMillis();
            Date issuedAtTime = new Date(presentTime);
            Date expirationTime = new Date(presentTime + APP_EXPIRE_AT_ON_MILLIS);

            SecretKey secretKey = generalKey();
            JwtBuilder builder = Jwts.builder()
                    .setId(userId)
                    // 主题
                    .setSubject(subject)
                    // 签发者
                    .setIssuer(JWT_ID)
                    // 签发时间
                    .setIssuedAt(issuedAtTime)
                    .signWith(secretKey)
                    // 过期时间
                    .setExpiration(expirationTime);
            return builder.compact();
        } catch (Exception e) {
            log.error("颁发token失败，参数userId={},subject={},异常信息：{}", userId, subject, e.toString());
            throw new Exception("颁发APP端token失败，异常信息：", e);
        }
    }

    /**
     * 验证App 的 Jwt token
     *
     * @param jwtStr
     * @return
     */
    public static Claims validateJwtForApp(String jwtStr) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr).getBody();
    }

    /**
     * 设置key值
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(APP_TOKEN_SECRET);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
        return key;
    }

}
