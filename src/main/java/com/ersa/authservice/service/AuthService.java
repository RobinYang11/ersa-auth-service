package com.ersa.authservice.service;

public interface AuthService {


    /**
     * 生成token
     * @param uid
     * @param password
     * @return
     */
    String generateToken(String uid,String password);

}
