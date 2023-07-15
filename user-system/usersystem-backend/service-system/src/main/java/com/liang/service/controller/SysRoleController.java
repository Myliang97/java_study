package com.liang.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.common.log.Log;
import com.liang.common.result.Result;
import com.liang.model.system.SysRole;
import com.liang.model.vo.SysRoleQueryVo;
import com.liang.service.mapper.SysRoleMapper;
import com.liang.service.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleService sysRoleService;


    @Log(value = "getRoleById")
    @ApiOperation(value = "根据id获取角色")
    @GetMapping("{id}")
    public Result getRoleById(@PathVariable("id") String id){

        SysRole byId = sysRoleService.getById(id);
        return Result.ok(byId);
    }

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("/findAll")
    public Result findAll() {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        return Result.ok(sysRoles);
    }

    @ApiOperation(value = "分页获取数据")
    @GetMapping("{page}/{limit}")
    public Result getSysRolePage(@PathVariable("page") Integer page,
                                 @PathVariable("limit") Integer limit,
                                 SysRoleQueryVo vo){

        Page<SysRole> pageParam = new Page<>(page, limit);
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam, vo);
        return Result.ok(pageModel);
    }


    @ApiOperation(value = "添加角色")
    @PostMapping("add")
    public Result addRole(@RequestBody SysRole role){

        sysRoleService.save(role);
        return Result.ok();
    }

    @ApiOperation(value = "更新角色")
    @PostMapping("update")
    public Result updateRole(@RequestBody SysRole role){
        sysRoleService.updateById(role);
        return Result.ok();
    }

    @ApiOperation(value = "根据id移除角色")
    @DeleteMapping("/remove/{id}")
    public Result deleteSysRole(@PathVariable("id") String id){

        //int a = 10/0;
        int i = sysRoleMapper.deleteById(id);
        return Result.ok(i);
    }

    @ApiOperation(value = "根据id列表移除角色")
    @DeleteMapping("/batchRemove")
    public Result deleteSysRoleByList(@RequestBody List<String> idList){

        sysRoleService.removeByIds(idList);
        return Result.ok();
    }
}
