package cqwu.edu.diary.service.security.handler;

import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.utils.ResponseResultUtil;
import cqwu.edu.diary.service.security.jwt.JwtTokenUtil;
import cqwu.edu.diary.service.config.JwtConfig;
import cqwu.edu.diary.service.security.entity.CustomerUserEntity;
import cqwu.edu.diary.service.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理类,事项AuthenticationSuccessHandler接口
 *
 */
@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource(name = "sysUserService")
    private ISysUserService sysUserService;

    /**
     * 登录认证返回结果
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        CustomerUserEntity customerUserEntity = (CustomerUserEntity) authentication.getPrincipal();
        //  从数据库中获取用户信息
        SysUserEntity sysUserEntity = sysUserService.selectUserByName(customerUserEntity.getUsername());
        Map<String, Object> resultData = new HashMap<>(2);
        String token = JwtTokenUtil.createAccessToken(customerUserEntity);
        token = JwtConfig.tokenPrefix + token;
        resultData.put("code", 200);
        resultData.put("message", "认证成功");
        resultData.put("token", token);
        resultData.put("data", sysUserEntity);
        ResponseResultUtil.responseJson(httpServletResponse, resultData);

    }
}
