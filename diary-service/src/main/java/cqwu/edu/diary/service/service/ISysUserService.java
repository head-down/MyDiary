package cqwu.edu.diary.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cqwu.edu.diary.common.dto.RegisterDTO;
import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.vo.SysUserVO;
import cqwu.edu.diary.service.mapper.SysUserMapper;

public interface ISysUserService extends IService<SysUserEntity> {

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 用户对象
     */
    SysUserEntity selectUserByName(final String username);

    /**
     * 查询当前登录人用户信息
     */
    SysUserVO queryLoginUser();

    /**
     * 注册用户
     * @param dto 注册参数对象
     */
    void Register(final RegisterDTO dto);
}
