package cqwu.edu.diary.service.controller;

import cqwu.edu.diary.common.dto.RegisterDTO;
import cqwu.edu.diary.common.response.CustomReturn;
import cqwu.edu.diary.common.vo.SysUserVO;
import cqwu.edu.diary.service.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/sysUser")
@Validated
public class SysUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private ISysUserService sysUserService;

    @GetMapping("/getUser")
    public CustomReturn<SysUserVO> queryLoginUser(){
        SysUserVO userVO = sysUserService.queryLoginUser();
        LOGGER.info("userVO:{}",userVO);
        return CustomReturn.success(userVO);
    }

    @PostMapping("/register")
    public CustomReturn<Void> register(@RequestBody @Valid RegisterDTO dto){
        sysUserService.Register(dto);
        return CustomReturn.success("注册成功");
    }

    /**
     * 上传头像
     * @param file 头像文件
     * @return 自定义返回对象
     */
    @PostMapping("/uploadProfile")
    public CustomReturn<Void> uploadProfile(@RequestBody @NotNull(message = "文件为空") final MultipartFile file){
        LOGGER.info("file:{}",file);
        sysUserService.uploadProfile(file);
        return CustomReturn.success("上传成功");
    }

}
