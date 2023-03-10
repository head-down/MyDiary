package cqwu.edu.diary.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cqwu.edu.diary.common.dto.RegisterDTO;
import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.utils.SnowflakeDistributeId;
import cqwu.edu.diary.common.vo.SysUserVO;
import cqwu.edu.diary.service.mapper.SysUserMapper;
import cqwu.edu.diary.service.security.util.SecurityUtil;
import cqwu.edu.diary.service.service.IFileInfoService;
import cqwu.edu.diary.service.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 用户服务实现类
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private IFileInfoService fileInfoService;

    @Value("${file.profile}")
    private String profileDefault;

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public SysUserEntity selectUserByName(final String username) {
        return lambdaQuery().eq(SysUserEntity::getUsername, username)
                .last("limit 1")
                .one();
    }

    /**
     * 查询当前登录人用户信息
     */
    @Override
    public SysUserVO queryLoginUser() {
        final Long id = SecurityUtil.getId();
        LOGGER.info("id:{}", id);
        SysUserVO userVO = sysUserMapper.selectLoginUser(id);
        if(userVO.getProfile() == null){
            userVO.setProfile(profileDefault);
        }
        return userVO;
    }

    /**
     * 注册用户
     *
     * @param dto 注册参数对象
     */
    @Override
    public void Register(final RegisterDTO dto) {
        final SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(dto, userEntity);
        userEntity.setId(SnowflakeDistributeId.getInstance().nextId());
        final String password = new BCryptPasswordEncoder().encode(dto.getPassword());
        userEntity.setPassword(password);
        sysUserMapper.insert(userEntity);
    }

    @Override
    public void uploadProfile(final MultipartFile file) {
        LOGGER.info("file:{}", file);
        Boolean isUploaded = fileInfoService.uploadImage(file);
        return;
    }
}
