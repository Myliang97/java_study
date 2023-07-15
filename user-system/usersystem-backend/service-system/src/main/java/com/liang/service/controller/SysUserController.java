package com.liang.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.common.result.Result;
import com.liang.common.util.MD5;
import com.liang.model.system.SysUser;
import com.liang.model.vo.SysRoleQueryVo;
import com.liang.model.vo.SysUserQueryVo;
import com.liang.service.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "根据id获取用户信息")
    @GetMapping("{id}")
    public Result getUserById(@PathVariable("id") String id){

        SysUser sysUser = sysUserService.getById(id);
        return  Result.ok(sysUser);
    }


    @ApiOperation(value = "获取全部用户列表")
    @GetMapping("/list")
    public Result findAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_deleted",0);
        List<SysUser> sysRoles = sysUserService.list(null);
        return Result.ok(sysRoles);
    }

    @ApiOperation(value = "分页获取数据")
    @GetMapping("{page}/{limit}")
    public Result getSysRolePage(@PathVariable("page") Integer page,
                                 @PathVariable("limit") Integer limit,
                                 SysUserQueryVo vo){

        Page<SysUser> pageParam = new Page<>(page, limit);
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam, vo);
        return Result.ok(pageModel);
    }


    @ApiOperation(value = "添加用户")
    @PostMapping("add")
    public Result addRole(@RequestBody SysUser user){

        String ps = MD5.encrypt(user.getPassword());
        user.setPassword(ps);
        sysUserService.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户")
    @PostMapping("update")
    public Result updateRole(@RequestBody SysUser user){

        String password = user.getPassword();
        if(password != null && !password.isEmpty())
        {
            String ps = MD5.encrypt(user.getPassword());
            user.setPassword(ps);
        }

        sysUserService.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "根据id移除用户")
    @DeleteMapping("/remove/{id}")
    public Result deleteSysRole(@PathVariable("id") String id){

        boolean b = sysUserService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表移除用户")
    @DeleteMapping("/batchRemove")
    public Result deleteSysRoleByList(@RequestBody List<String> idList){

        sysUserService.removeByIds(idList);
        return Result.ok();
    }
}
