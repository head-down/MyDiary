package cqwu.edu.diary.service.mapper;

import cqwu.edu.diary.common.entity.UserDiaryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cqwu.edu.diary.common.vo.UserDiaryVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jianghr
 * @since 2023-01-29
 */
public interface UserDiaryMapper extends BaseMapper<UserDiaryEntity> {

    /**
     * 查询登录用户的日记id和创建日期
     * @param userId 用户id
     * @return 日记返回对象列表
     */
    List<UserDiaryVO> selectUserDiaries(Long userId);
}
