package com.liang.microserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liang.UserService;

@RestController
@RequestMapping("/dev-api")
public class UserController {
    
    @Reference
    private UserService userService;

    @GetMapping("/user")
    public String get(){
        return userService.getUser();
    }

}
