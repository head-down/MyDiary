package cqwu.edu.diary.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cqwu.edu.diary.common.dto.RegisterDTO;
import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.utils.SnowflakeDistributeId;
import cqwu.edu.diary.common.vo.SysUserVO;
import cqwu.edu.diary.service.config.SecurityConfig;
import cqwu.edu.diary.service.mapper.SysUserMapper;
import cqwu.edu.diary.service.security.util.SecurityUtil;
import cqwu.edu.diary.service.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUserEntity> implements ISysUserService{

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public SysUserEntity selectUserByName(String username) {
        return lambdaQuery().eq(SysUserEntity::getUsername,username)
                .last("limit 1")
                .one();
    }

    /**
     * 查询当前登录人用户信息
     */
    @Override
    public SysUserVO queryLoginUser() {
        final Long id = SecurityUtil.getId();
        final SysUserEntity userEntity = lambdaQuery().eq(SysUserEntity::getId, id).one();
        final SysUserVO userVO = new SysUserVO();
        BeanUtils.copyProperties(userEntity,userVO);
        return userVO;
    }

    /**
     * 注册用户
     * @param dto 注册参数对象
     */
    @Override
    public void Register(RegisterDTO dto) {
        final SysUserEntity userEntity = new SysUserEntity();
        userEntity.setId(SnowflakeDistributeId.getInstance().nextId());
        final String password = new BCryptPasswordEncoder().encode(dto.getPassword());
        userEntity.setPassword(password);
        sysUserMapper.insert(userEntity);
    }
}
