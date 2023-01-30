package cqwu.edu.diary.service.service;

import cqwu.edu.diary.common.entity.UserDiaryEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import cqwu.edu.diary.common.response.CustomReturn;
import cqwu.edu.diary.common.vo.UserDiaryVO;

import java.util.List;

/**
 * <p>
 *  日记服务类
 * </p>
 *
 * @author jianghr
 * @since 2023-01-29
 */
public interface IUserDiaryService extends IService<UserDiaryEntity> {

    /**
     * 查询登录用户的日记id和创建日期
     * @return 日志返回对象
     */
    List<UserDiaryVO> queryUserDiaries();
}
