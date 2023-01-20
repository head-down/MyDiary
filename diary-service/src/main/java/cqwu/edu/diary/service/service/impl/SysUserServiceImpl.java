package cqwu.edu.diary.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.vo.SysUserVO;
import cqwu.edu.diary.service.mapper.SysUserMapper;
import cqwu.edu.diary.service.security.util.SecurityUtil;
import cqwu.edu.diary.service.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUserEntity> implements ISysUserService{

    @Override
    public SysUserEntity selectUserByName(String username) {
        return lambdaQuery().eq(SysUserEntity::getUsername,username)
                .last("limit 1")
                .one();
    }

    @Override
    public SysUserVO queryLoginUser() {
        final Long id = SecurityUtil.getId();
        final SysUserEntity userEntity = lambdaQuery().eq(SysUserEntity::getId, id).one();
        final SysUserVO userVO = new SysUserVO();
        BeanUtils.copyProperties(userEntity,userVO);
        return userVO;
    }
}
