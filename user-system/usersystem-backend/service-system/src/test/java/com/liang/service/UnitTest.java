package com.liang.service;

import com.liang.common.helper.JwtHelper;
import com.liang.model.system.SysRole;
import com.liang.service.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UnitTest {


    @Autowired
    SysRoleMapper sysRoleMapper;

    @Test
    void testRole(){

        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        for (SysRole sysrole:sysRoles) {
            System.out.println(sysrole);
        }
    }

    @Test
    void testJWT(){
        String token = JwtHelper.createToken("12345", "test");
        String userId = JwtHelper.getUserId(token);
        String username = JwtHelper.getUsername(token);
        System.out.println(userId + username);
    }
}
