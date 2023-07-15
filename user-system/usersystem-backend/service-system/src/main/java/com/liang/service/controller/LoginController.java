package com.liang.service.controller;

import com.liang.common.helper.JwtHelper;
import com.liang.common.result.Result;
import com.liang.common.result.ResultCodeEnum;
import com.liang.common.util.MD5;
import com.liang.model.system.SysUser;
import com.liang.model.vo.LoginVo;
import com.liang.common.exception.CustomException;
import com.liang.service.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(tags = "登录")
@RestController
@RequestMapping("/admin/system/index")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    //开启secutity后，这里登录已经无效
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){

        //{"code":20000,"data":{"token":"admin-token"}}

        SysUser sysUser = sysUserService.getUserByName(loginVo.getUsername());
        if(sysUser == null){
            throw new CustomException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PASSWORD_ERROR);
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new CustomException(ResultCodeEnum.ACCOUNT_STOP);
        }

        Map<String,String> m = new HashMap<>();
        m.put("token", JwtHelper.createToken(sysUser.getId(),loginVo.getUsername()));
        return Result.ok(m);
    }

    @GetMapping("info")
    public Result info(HttpServletRequest request){

        /*
        * {
        "code": 20000,
        "data": {
            "roles": [
                "admin"
            ],
            "introduction": "I am a super administrator",
            "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
            "name": "Super Admin"
            }
        }
        * */
        String token = request.getHeader("token");


        String username = JwtHelper.getUsername(token);
        String userId = JwtHelper.getUserId(token);

        System.out.println(username + userId);

        Map<String,String> m = new HashMap<>();
        m.put("roles","[admin]");
        m.put("introduction","超级管理员");
        m.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        m.put("name","Super Admin");
        return Result.ok(m);
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
