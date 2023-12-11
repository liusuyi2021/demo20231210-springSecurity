package com.lsy.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName JwtAuthenticationLoginFilter
 * @Description: 身份认证登录过滤器(不用自己写login业务)
 * @Author 刘苏义
 * @Date 2023/12/11 20:36
 * @Version 1.0
 */

@Slf4j
public class JwtAuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 构造方法，调用父类的，设置登录地址/login，请求方式POST
     */
    public JwtAuthenticationLoginFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        //获取表单提交数据
        Map<String, String> map;
        UsernamePasswordAuthenticationToken authRequest = null;
        try {
            map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = map.get("userName");
            String password = map.get("password");
            //String srcPassword = AESUtils.decrypt(password);
            //
            //封装到token中提交
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            log.error("读取登录的用户名和密码参数错误！");
            e.printStackTrace();
        } finally {
            return getAuthenticationManager().authenticate(authRequest);
        }
    }
}