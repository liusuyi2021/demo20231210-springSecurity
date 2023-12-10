package com.lsy.service;

import com.lsy.entity.SysUser;
import com.lsy.utils.AjaxResult;

/**
 * @ClassName ILoginService
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 15:16
 * @Version 1.0
 */
public interface ILoginService {
    AjaxResult login(SysUser sysUser);
    AjaxResult logout();
}
