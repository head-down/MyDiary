package cqwu.edu.diary.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "RegisterDTO",description = "注册参数对象")
public class RegisterDTO {

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank
    private String userName;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank
    private String password;

}
