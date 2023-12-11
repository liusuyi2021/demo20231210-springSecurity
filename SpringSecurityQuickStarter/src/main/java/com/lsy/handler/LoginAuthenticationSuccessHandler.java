package com.lsy.handler;

import com.alibaba.fastjson2.JSON;
import com.lsy.entity.LoginUser;
import com.lsy.utils.AjaxResult;
import com.lsy.utils.JwtUtil;
import com.lsy.utils.RedisCache;
import com.lsy.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功操作
 *
 * @author 公众号：码猿技术专栏
 */
@Component
@Slf4j
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Resource
    private RedisCache redisCache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        log.info(String.format("IP %s，用户 %s， 于 %s 登录系统。",
                httpServletRequest.getRemoteHost(), authentication.getName(), LocalDateTime.now()));
        //
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userId = loginUser.getSysUser().getUserId().toString();
        //生成令牌
        String accessToken = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", accessToken);

        //将token存入redis
        redisCache.setCacheObject("login:" + userId,loginUser);
        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(AjaxResult.success("登录成功！", map)));

    }

}