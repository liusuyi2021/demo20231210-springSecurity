package com.lsy.handler;

import com.alibaba.fastjson2.JSON;
import com.lsy.entity.LoginUser;
import com.lsy.utils.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @ClassName JwtLogoutSuccessHandler
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/11 20:54
 * @Version 1.0
 */
@Component
@Slf4j
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private RedisCache redisCache;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("token为空");
        }
        //解析token
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if(Objects.nonNull(loginUser)) {
            log.info(String.format("IP %s，用户 %s， 于 %s 退出系统。",
                    request.getRemoteHost(), loginUser.getSysUser().getUserName(), LocalDateTime.now()));
            //redis中移除用户
            redisCache.deleteObject("login:" + userId);
        }

        String msg = StringUtils.format("用户【" + userId + "】注销成功！");
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success(msg)));
    }
}
