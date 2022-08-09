package com.ersa.authservice.ctrl;


import com.ersa.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

}

