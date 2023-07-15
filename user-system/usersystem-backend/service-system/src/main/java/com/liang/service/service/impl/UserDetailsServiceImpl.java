package com.liang.service.service.impl;

import com.liang.common.result.ResultCodeEnum;
import com.liang.model.system.SysUser;
import com.liang.common.exception.CustomException;
import com.liang.service.service.SysUserService;
import com.liang.util.SysUserdetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = sysUserService.getUserByName(username);
        if(sysUser == null){
            throw new CustomException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new CustomException(ResultCodeEnum.ACCOUNT_STOP);
        }
        return new SysUserdetails(sysUser, Collections.emptyList());
    }
}
