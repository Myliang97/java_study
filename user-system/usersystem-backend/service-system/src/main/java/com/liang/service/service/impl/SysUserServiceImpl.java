package com.liang.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.model.system.SysUser;
import com.liang.model.vo.SysUserQueryVo;
import com.liang.service.mapper.SysUserMapper;
import com.liang.service.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl  extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> pageModel = baseMapper.selectPage(pageParam,sysUserQueryVo);
        return pageModel;
    }

    @Override
    public SysUser getUserByName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        SysUser sysUser = baseMapper.selectOne(queryWrapper);
        return sysUser;
    }
}
