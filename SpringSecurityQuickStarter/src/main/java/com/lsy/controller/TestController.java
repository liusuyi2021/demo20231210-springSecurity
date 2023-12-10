package com.lsy.controller;

import com.lsy.entity.SysUser;
import com.lsy.mapper.SysUserMapper;
import com.lsy.utils.AjaxResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TestController
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 13:21
 * @Version 1.0
 */
@RestController
public class TestController {

    @Resource
    SysUserMapper sysUserMapper;
    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('system:dept:list')")
    AjaxResult hello() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        return AjaxResult.success(sysUsers);
    }
}
