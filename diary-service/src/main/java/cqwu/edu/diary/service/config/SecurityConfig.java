package cqwu.edu.diary.service.config;

import cqwu.edu.diary.service.security.UserAuthenticationProvider;
import cqwu.edu.diary.service.security.UserPermissionEvaluator;
import cqwu.edu.diary.service.security.handler.*;
import cqwu.edu.diary.service.security.jwt.JwtAuthenticationTokenFilter;
import cqwu.edu.diary.service.security.service.CustomerUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// 开启权限注解，默认是关闭的
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义登录成功处理器
     */
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    /**
     * 自定义登录失败处理类
     */
    @Resource
    private LoginFailureHandler loginFailureHandler;

    /**
     * 自定义暂无权限处理类
     */
    @Resource
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;

    /**
     * 自定义未登录处理类
     */
    @Resource
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;

    /**
     * 自定义逻辑验证处理类
     */
    @Resource
    private UserAuthenticationProvider userAuthenticationProvider;

    /**
     * 自定义登出成功处理器
     */
    @Resource
    private UserLogoutSuccessHandler logoutSuccessHandler;


    /**
     * 加密方式
     *
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入自定义PermissionEvaluator
     *
     * @return {@link DefaultWebSecurityExpressionHandler}
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置登录验证逻辑
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  启用自定义的登录逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }

    /**
     * Security逻辑控制
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("JwtConfig信息打印:{}", JwtConfig.antMatchers);
        http.authorizeRequests()
                //  不进行权限验证的请求从配置文件中获取
                .antMatchers(JwtConfig.antMatchers.split(","))
                .permitAll()
                //  其它的请求需要登录验证
                .anyRequest()
                .authenticated()
                .and()
                //  配置未登录自定义处理类
                .httpBasic()
                .authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                //  配置登录地址
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                //  配置自定义登录成功处理类
                .successHandler(loginSuccessHandler)
                //  配置登录失败自定义处理类
                .failureHandler(loginFailureHandler)
                .and()
                .logout()
                //  配置登出地址
                .logoutUrl("/logout")
                //  配置用户登出自定义处理类
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling()
                //  配置没有权限自定义处理类
                .accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                //  开启跨域
                .cors().configurationSource(configurationSource())
                .and()
                //  取消跨站请求伪造防护
                .csrf().disable();
        //  基于token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //  禁用缓存
        http.headers().cacheControl();
        //  添加JWT过滤器
        http.addFilter(new JwtAuthenticationTokenFilter(authenticationManager()));
    }

    //跨域配置
    CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
