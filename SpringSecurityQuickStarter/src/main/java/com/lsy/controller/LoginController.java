package com.lsy.controller;

import com.lsy.entity.SysUser;
import com.lsy.service.impl.LoginServiceImpl;
import com.lsy.utils.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName LoginController
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 15:13
 * @Version 1.0
 */
@RestController
public class LoginController {

    @Resource
    LoginServiceImpl loginService;

    @PostMapping("/user/login")
    public AjaxResult login(@RequestBody SysUser sysUser)
    {
        return loginService.login(sysUser);
    }
    @PostMapping("/user/logout")
    public AjaxResult logout()
    {
        return loginService.logout();
    }
}
