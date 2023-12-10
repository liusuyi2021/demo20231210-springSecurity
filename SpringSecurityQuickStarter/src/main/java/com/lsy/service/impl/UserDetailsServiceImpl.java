package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsy.entity.LoginUser;
import com.lsy.entity.SysUser;
import com.lsy.mapper.SysMenuMapper;
import com.lsy.mapper.SysUserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName ISysUserService
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 13:48
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysMenuMapper sysMenuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper=new  LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUserName,username);
        SysUser sysUser = sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
        if(Objects.isNull(sysUser))
        {
          throw new RuntimeException("用户名或者密码错误!!!");
        }
        //TODO:查询对应的权限信息

//        List<String> permissions=new ArrayList<>(Arrays.asList("admin"));
        List<String> permissions=sysMenuMapper.selectPermsByUserId(sysUser.getUserId());
        //封装UserDetails
        return new LoginUser(sysUser,permissions);
    }
}
