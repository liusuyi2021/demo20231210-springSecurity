package com.lsy.filter;

import com.lsy.entity.LoginUser;
import com.lsy.utils.JwtUtil;
import com.lsy.utils.RedisCache;
import com.lsy.utils.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 16:11
 * @Version 1.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token无效");
        }
        //从redis中获取登录的用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);

        //存入SecurityContextHolder
        //TODO:获取权限信息封装到authenticationToken
        if(Objects.isNull(loginUser))
        {
            throw new RuntimeException("token无效");
        }
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        context.setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
