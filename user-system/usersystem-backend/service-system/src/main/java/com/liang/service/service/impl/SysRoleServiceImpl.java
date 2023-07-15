package com.liang.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.model.system.SysRole;
import com.liang.model.vo.SysRoleQueryVo;
import com.liang.service.mapper.SysRoleMapper;
import com.liang.service.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl  extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService  {
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel = baseMapper.selectPage(pageParam,sysRoleQueryVo);
        return pageModel;
    }
}
