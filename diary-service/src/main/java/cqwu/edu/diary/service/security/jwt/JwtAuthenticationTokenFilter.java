package cqwu.edu.diary.service.security.jwt;

import com.alibaba.fastjson.JSON;
import cqwu.edu.diary.service.config.JwtConfig;
import cqwu.edu.diary.service.security.entity.CustomerUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Jwt接口请求拦截,请求接口会在这个类中验证token是否合法和过期
 *
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //  获取请求中的token
        String tokenHeader = request.getHeader(JwtConfig.tokenHeader);
        if (StringUtils.isNotEmpty(tokenHeader) && tokenHeader.startsWith(JwtConfig.tokenPrefix)) {
            try {
                String token = tokenHeader.replace(JwtConfig.tokenPrefix, "");
                //  解析JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(JwtConfig.secret)
                        .parseClaimsJws(token)
                        .getBody();
                String username = claims.getSubject();
                String userId = claims.getId();
                if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(username)) {
                    //  角色列表
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String authority = claims.get("authorities").toString();
                    if (StringUtils.isNotEmpty(authority)) {
                        //此处需要优化代码
                        List<Map<String, String>> authorityList = JSON.parseObject(authority, List.class);
                        for (Map<String, String> roleMap : authorityList) {
                            authorities.add(new SimpleGrantedAuthority(roleMap.get("authority")));
                        }
                    }
                    CustomerUserEntity customerUserEntity = new CustomerUserEntity();
                    customerUserEntity.setUsername(username);
                    customerUserEntity.setId(Long.parseLong(userId));
                    customerUserEntity.setAuthorities(authorities);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customerUserEntity, userId, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (ExpiredJwtException ex) {
                log.info("token过期!");
            } catch (Exception ex) {
                //TODO 异常封装
                log.info("token无效");
            }
        }
        chain.doFilter(request, response);
    }
}