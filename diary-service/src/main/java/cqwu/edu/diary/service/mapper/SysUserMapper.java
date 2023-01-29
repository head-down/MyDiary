package cqwu.edu.diary.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cqwu.edu.diary.common.entity.SysUserEntity;
import cqwu.edu.diary.common.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 查询当前登录用户信息
     * @param id 用户id
     * @return 用户返回对象
     */
    SysUserVO selectLoginUser(Long id);
}
