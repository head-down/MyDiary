package cqwu.edu.diary.service.security.util;

import cqwu.edu.diary.service.security.entity.CustomerUserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security工具类
 *
 */
public class SecurityUtil {

    private SecurityUtil() {

    }

    /**
     * 获取当前用户信息
     *
     * @return {@link CustomerUserEntity}
     */
    public static CustomerUserEntity getUserInfo() {
        return (CustomerUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取当前用户ID
     *
     * @return {@link Long}
     */
    public static Integer getId() {
        return getUserInfo().getId();
    }

    /**
     * 获取当前用户账号
     *
     * @return {@link String}
     */
    public static String getUsername() {
        return getUserInfo().getUsername();
    }
}
