package cqwu.edu.diary.service.controller;

import cqwu.edu.diary.common.response.CustomReturn;
import cqwu.edu.diary.common.vo.SysUserVO;
import cqwu.edu.diary.service.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sysUser")
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

}
