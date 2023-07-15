package com.liang.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.liang.UserService;

@Service
public class UserServiceImpl  implements UserService{
    
    @Override
    public String getUser(){
        return "{user}";
    }
}
