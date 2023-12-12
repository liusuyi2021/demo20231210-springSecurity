package com.lsy.config;

import com.lsy.filter.JwtAuthenticationTokenFilter;
import com.lsy.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @ClassName SecurityConfig
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 14:49
 * @Version 1.0
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    AuthenticationEntryPointImpl authenticationEntryPointImpl;
    @Resource
    AccessDeniedHandlerImpl accessDeniedHandlerImpl;
    @Resource
    JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    @Resource
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable().apply(jwtAuthenticationSecurityConfig);
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                //对于登录接口 允许匿名访问
                .antMatchers("/login").permitAll()
                //除上面请求外所有请求都需要鉴权
                .anyRequest().authenticated();
        http.exceptionHandling()
                //认证未通过，不允许访问，异常处理器
                .authenticationEntryPoint(authenticationEntryPointImpl)
                //认证通过，但是没权限处理器
                .accessDeniedHandler(accessDeniedHandlerImpl);

        http.logout().logoutSuccessHandler(jwtLogoutSuccessHandler);
        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
