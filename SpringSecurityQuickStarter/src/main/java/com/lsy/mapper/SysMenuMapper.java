package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @ClassName SysMenu
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 18:03
 * @Version 1.0
 */
public interface SysMenuMapper extends BaseMapper<SysMenuMapper> {

    List<String> selectPermsByUserId(Long userId);
}
