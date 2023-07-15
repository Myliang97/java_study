package com.liang.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;;
import com.liang.model.system.SysUser;
import com.liang.model.vo.SysUserQueryVo;

public interface SysUserService extends IService<SysUser> {

    //条件分页查询
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    SysUser getUserByName(String username);
}
