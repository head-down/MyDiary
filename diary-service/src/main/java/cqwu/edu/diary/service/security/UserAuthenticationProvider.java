package cqwu.edu.diary.service.security;

import cqwu.edu.diary.service.security.entity.CustomerUserEntity;
import cqwu.edu.diary.service.security.service.CustomerUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义登录验证
 *
 */
@Component
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private CustomerUserDetailService customerUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        CustomerUserEntity customerUser = customerUserDetailService.loadUserByUsername(username);
        log.info("【username={},password={}】", username, password);
        if (customerUser == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        if (!new BCryptPasswordEncoder().matches(password, customerUser.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        //TODO 登录信息扩展

        //  角色列表
        Set<GrantedAuthority> authoritySet = new HashSet<>();

        //TODO 查询DB中的数据
        // 查询用户角色
//        List<SysRoleEntity> sysRoleEntityList = sysRoleService.selectSysRoleByUserId(customerUser.getUserId());
//        for (SysRoleEntity sysRoleEntity: sysRoleEntityList){
//            authoritySet.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getRoleName()));
//        }
        customerUser.setAuthorities(authoritySet);
        //  登录
        return new UsernamePasswordAuthenticationToken(customerUser, password, authoritySet);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
