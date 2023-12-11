package com.lsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName SpringSecurityQuickStarterApplication
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 13:19
 * @Version 1.0
 */
@MapperScan("com.lsy.mapper")
@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }
}
