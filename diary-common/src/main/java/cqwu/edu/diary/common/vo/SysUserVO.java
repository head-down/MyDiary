package cqwu.edu.diary.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserVO {

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;
}
