package com.lsy;

import com.lsy.entity.SysUser;
import com.lsy.mapper.SysMenuMapper;
import com.lsy.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName com.lsy.MyTest
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 13:27
 * @Version 1.0
 */
@SpringBootTest
public class MyTest {

    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    SysMenuMapper sysMenuMapper;
    @Test
    void test() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(sysUsers);
    }

    @Test
    void passwordTest() {
        String encode = passwordEncoder.encode("12345");
        boolean matchesRes = passwordEncoder.matches("123456", "$2a$10$inQkr7sYjdOUF4IH13TchOkI1vxucfVXeg5FrzADXTuiXzbBQkpu6");
        System.out.println(matchesRes);
    }
    @Test
    void menuTest()
    {
        List<String> strings = sysMenuMapper.selectPermsByUserId(1L);
        System.out.println(strings);

    }
}
