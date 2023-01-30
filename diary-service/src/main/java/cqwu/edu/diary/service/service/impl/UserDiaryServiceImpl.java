package cqwu.edu.diary.service.service.impl;

import cqwu.edu.diary.common.entity.UserDiaryEntity;
import cqwu.edu.diary.common.vo.UserDiaryVO;
import cqwu.edu.diary.service.mapper.UserDiaryMapper;
import cqwu.edu.diary.service.security.util.SecurityUtil;
import cqwu.edu.diary.service.service.IUserDiaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  日志服务实现类
 * </p>
 *
 * @author jianghr
 * @since 2023-01-29
 */
@Service("diaryService")
public class UserDiaryServiceImpl extends ServiceImpl<UserDiaryMapper, UserDiaryEntity> implements IUserDiaryService {

    @Resource
    private UserDiaryMapper diaryMapper;

    /**
     * 查询登录用户的日记id和创建日期
     * @return 日志返回对象列表
     */
    @Override
    public List<UserDiaryVO> queryUserDiaries() {
        final Long userId = SecurityUtil.getId();
        return diaryMapper.selectUserDiaries(userId);
    }
}
