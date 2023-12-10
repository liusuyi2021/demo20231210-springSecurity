package com.lsy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SysUser
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 13:49
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    @TableId
    Long userId;
    String nickName;
    String userName;
    String password;
    Date createTime;
    Date updateTime;
    Integer status;
}
