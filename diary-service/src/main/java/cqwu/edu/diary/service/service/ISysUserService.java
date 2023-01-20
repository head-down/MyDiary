package cqwu.edu.diary.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.vo.SysUserVO;
import cqwu.edu.diary.service.mapper.SysUserMapper;

public interface ISysUserService extends IService<SysUserEntity> {

    SysUserEntity selectUserByName(final String username);

    SysUserVO queryLoginUser();
}
