package cqwu.edu.diary.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class SysUserEntity extends BaseEntity{

    @TableField(value = "USERNAME")
    private String username;

    @TableField(value = "PASSWORD")
    private String password;

    @TableField(value = "STATUS")
    private String status;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "MOBILE")
    private String mobile;

    @TableField(value = "EMAIL")
    private String email;

}

