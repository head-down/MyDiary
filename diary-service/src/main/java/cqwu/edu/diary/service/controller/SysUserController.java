package cqwu.edu.diary.service.controller;
import java.time.LocalDateTime;

import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.response.CustomReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @GetMapping("/getUser")
    public CustomReturn<SysUserEntity> getUser(){
        final SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername("12312");
        sysUserEntity.setPassword("123123");
        sysUserEntity.setStatus("123132");
        sysUserEntity.setName("123123");
        return CustomReturn.success(sysUserEntity);
    }

}
