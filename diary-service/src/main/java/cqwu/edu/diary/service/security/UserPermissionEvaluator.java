package cqwu.edu.diary.service.security;

import cqwu.edu.diary.service.security.entity.CustomerUserEntity;
import cqwu.edu.diary.service.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义注解权限验证
 *
 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    @Resource(name = "sysUserService")
    private ISysUserService sysUserService;

    /**
     * 判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     *
     * @param authentication
     * @param targetUrl
     * @param permission
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        CustomerUserEntity customerUserEntity = (CustomerUserEntity) authentication.getPrincipal();
        //  查询用户信息，后续可以将用户权限信息放到缓存中，不用每次都在DB中获取
        Set<String> permissions = new HashSet<>();
        //TODO 查询DB中的数据

        //TODO 此处代码可以优化  权限对比
        if (permissions.contains(permission)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
