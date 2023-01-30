package cqwu.edu.diary.service.controller;

import cqwu.edu.diary.common.response.CustomReturn;
import cqwu.edu.diary.common.vo.UserDiaryVO;
import cqwu.edu.diary.service.service.IUserDiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jianghr
 * @since 2023-01-29
 */
@RestController
@RequestMapping("/userDiary")
@Api(value = "/userDiary",tags = "用户日记接口")
public class UserDiaryController {

    @Resource
    private IUserDiaryService diaryService;

    /**
     * 查询用户所有日记的id和日期
     * @return 自定义返回对象
     */
    @GetMapping("/queryUserDiaries")
    @ApiOperation("查询用户所有日记")
    public CustomReturn<List<UserDiaryVO>> queryUserDiaries(){
        return CustomReturn.success(diaryService.queryUserDiaries());
    }

}
