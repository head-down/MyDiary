package cqwu.edu.diary.service.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Data
public class CustomerUserEntity implements Serializable, UserDetails {

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态：normal：正常 prohibit：禁用
     */
    private String status;
    /**
     * 用户名称中文
     */
    private String displayName;

    /**
     * 用户角色
     */
    private Collection<GrantedAuthority> authorities;
    /**
     * 账号是否过期
     */
    private boolean isAccountNonExpired = false;
    /**
     * 账号是否被锁定
     */
    private boolean isAccountNonLocked = false;
    /**
     * 证书是否过期
     */
    private boolean isCredentialNonExpired = false;
    /**
     * 账号是否有效
     */
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
