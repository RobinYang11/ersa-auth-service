package com.ersa.authservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
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

    public static final String APP_TOKEN_SECRET = "token unique encryption encoding";
    private static final String WEB_TOKEN_SECRET = "token";

    /**
     * JWT_ID
     */
    public static final String JWT_ID =

            UUID.randomUUID().toString();


    /**
     * 为Web端生成JWT token
     *
     * @param uid 当前登录的用户id
     * @return
     * @throws Exception
     */
    public static String issueWebJwtToken(long uid) throws Exception {
        Date expireDate = new Date(System.currentTimeMillis() + WEB_MAX_AGE_ON_SECOND * 1000);
        try {
            return JWT.create()
                    .withClaim("uid", uid)
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(WEB_TOKEN_SECRET));
        } catch (UnsupportedEncodingException e) {
            log.error("生成web端token失败，参数：uid = {}， 异常信息：{}", uid, e.toString());
            throw new Exception("生成web端token失败，异常信息：", e);
        }
    }

    /**
     * 为APP端生成JWT token
     *
     * @param userId
     * @return
     */
    public static String issueAppJwtToken(long userId, String subject) throws Exception {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            //设置过期时间
            long presentTime = System.currentTimeMillis();
            Date issuedAtTime = new Date(presentTime);
            Date expirationTime = new Date(presentTime + APP_EXPIRE_AT_ON_MILLIS);

            SecretKey secretKey = generalKey();
            JwtBuilder builder = Jwts.builder()
                    .setId(String.valueOf(userId))
                    // 主题
                    .setSubject(subject)
                    // 签发者
                    .setIssuer(JWT_ID)
                    // 签发时间
                    .setIssuedAt(issuedAtTime)
                    // 签名算法以及密匙
                    .signWith(signatureAlgorithm, secretKey)
                    // 过期时间
                    .setExpiration(expirationTime);
            return builder.compact();
        } catch (Exception e) {
            log.error("颁发APP端token失败，参数userId={},subject={},异常信息：{}", userId, subject, e.toString());
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
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * Web端的jwt解码
     *
     * @param token
     * @param key
     * @return
     */
    public static Claim jwtDecodingForWeb(String token, String key) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new Exception("cookies中不存在访问凭证");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(WEB_TOKEN_SECRET)).build();
        return jwtVerifier.verify(token).getClaim(key);
    }

}