package com.lsy.service.impl;

import com.lsy.entity.LoginUser;
import com.lsy.entity.SysUser;
import com.lsy.service.ILoginService;
import com.lsy.utils.AjaxResult;
import com.lsy.utils.JwtUtil;
import com.lsy.utils.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName LoginService
 * @Description: 自定义登录业务
 * @Author 刘苏义
 * @Date 2023/12/10 15:14
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    AuthenticationManager authenticationManager;
    @Resource
    RedisCache redisCache;

    @Override
    public AjaxResult login(SysUser sysUser) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
        //authustation
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String userId = loginUser.getSysUser().getUserId().toString();
        //生成令牌
        String token = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        //将token存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        return AjaxResult.success("登录成功", map);
    }

    @Override
    public AjaxResult logout() {
        //获取SecurityContextHolder中的userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();
        //删除redis中的值
        redisCache.deleteObject("login:" + userId);
        return AjaxResult.success("退出成功");
    }
}
