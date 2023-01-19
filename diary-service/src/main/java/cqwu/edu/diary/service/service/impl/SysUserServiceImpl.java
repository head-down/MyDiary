package cqwu.edu.diary.service.service.impl;

import com.baomidou.mybatisplus.extension.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.service.mapper.SysUserMapper;
import cqwu.edu.diary.service.service.ISysUserService;
import org.springframework.stereotype.Service;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUserEntity> implements ISysUserService{

    @Override
    public SysUserEntity selectUserByName(String username) {
        return lambdaQuery().eq(SysUserEntity::getUsername,username)
                .last("limit 1")
                .one();
    }
}
