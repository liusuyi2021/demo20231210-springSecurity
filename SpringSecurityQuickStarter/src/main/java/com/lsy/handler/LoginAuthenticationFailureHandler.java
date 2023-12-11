package com.lsy.handler;
import com.alibaba.fastjson2.JSON;
import com.lsy.utils.AjaxResult;
import com.lsy.utils.ServletUtils;
import com.lsy.utils.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败操作
 * @author 公众号：码猿技术专栏
 */
@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * 一旦登录失败则会被调用
     * @param httpServletRequest
     * @param response
     * @param exception 这个参数是异常信息，可以根据不同的异常类返回不同的提示信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        //BadCredentialsException 这个异常一般是用户名或者密码错误
        if (exception instanceof BadCredentialsException){
            ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(401,"用户名或密码不正确！")));
            exception.printStackTrace();
        }
        String msg = StringUtils.format("登录失败！");
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success(msg)));
    }
}