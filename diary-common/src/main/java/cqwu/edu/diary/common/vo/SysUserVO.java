package cqwu.edu.diary.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户返回对象")
public class SysUserVO {

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像地址")
    private String profile;
}
