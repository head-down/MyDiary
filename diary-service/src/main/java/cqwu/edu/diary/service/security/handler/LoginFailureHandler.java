package cqwu.edu.diary.service.security.handler;

import cqwu.edu.diary.common.utils.ResponseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理
 *
 * @author Ethan
 * @version V1.0
 * @email wangmin1@mochasoft.com.cn
 * @since 2022/11/9 14:22
 */
@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    /**
     * 登录失败返回结果
     *
     * @param equest
     * @param response
     * @param ex
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest equest, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        if (ex instanceof UsernameNotFoundException) {
            log.info("登录失败");
            ResponseResultUtil.responseJson(response, ResponseResultUtil.resultCode(500, "账号不存在"));
        }
        if (ex instanceof LockedException) {
            ResponseResultUtil.responseJson(response, ResponseResultUtil.resultCode(500, "账号被锁定"));
        }
        if (ex instanceof BadCredentialsException) {
            ResponseResultUtil.responseJson(response, ResponseResultUtil.resultCode(500, "账号或密码不正确"));
        }
        ResponseResultUtil.responseJson(response, ResponseResultUtil.resultCode(500, "登录失败"));
    }
}
