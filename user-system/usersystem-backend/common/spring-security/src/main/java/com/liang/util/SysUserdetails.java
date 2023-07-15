package com.liang.util;

import com.liang.model.system.SysUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class SysUserdetails extends User {

   private SysUser sysUser;

   public SysUserdetails(SysUser sysUser, Collection<? extends GrantedAuthority> authorities){
       super(sysUser.getUsername(),sysUser.getPassword(),authorities);
       this.sysUser = sysUser;
   }



}
