package com.lsy.handler;

import com.alibaba.fastjson2.JSON;
import com.lsy.utils.AjaxResult;
import com.lsy.utils.HttpStatus;
import com.lsy.utils.ServletUtils;
import com.lsy.utils.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AccessDeniedHandler
 * @Description:
 * @Author 刘苏义
 * @Date 2023/12/10 18:38
 * @Version 1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        int code = HttpStatus.FORBIDDEN;
        String msg = StringUtils.format("请求访问：{}，授权失败，您没有无权访问", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }
}
