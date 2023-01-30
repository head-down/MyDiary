package cqwu.edu.diary.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@ApiModel("日志返回对象")
@Data
public class UserDiaryVO {

    @ApiModelProperty("日志id")
    private Long diaryId;

    @ApiModelProperty("匹配对象日志id")
    private Long matchDiaryId;

    @ApiModelProperty("创建日期")
    private LocalDate date;

}
