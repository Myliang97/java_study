package com.liang.microserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev-api")
public class UserController {
    
    @GetMapping("/user")
    public String get(){
        return "user controller 8080";
    }

}
