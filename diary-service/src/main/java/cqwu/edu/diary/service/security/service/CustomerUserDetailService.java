package cqwu.edu.diary.service.security.service;

import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.service.security.entity.CustomerUserEntity;
import cqwu.edu.diary.service.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 查询DB中的数据
 *
 */
@Component
public class CustomerUserDetailService implements UserDetailsService {

    @Resource(name = "sysUserService")
    private ISysUserService sysUserService;

    /**
     * 查询用户信息
     *
     * @param username
     * @return {@link CustomerUserEntity}
     * @throws UsernameNotFoundException
     */
    @Override
    public CustomerUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity sysUserEntity = sysUserService.selectUserByName(username);
        if (sysUserEntity != null) {
            CustomerUserEntity customerUserEntity = new CustomerUserEntity();
            BeanUtils.copyProperties(sysUserEntity, customerUserEntity);
            return customerUserEntity;
        }
        return null;
    }
}
