package cqwu.edu.diary.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户日志对象
 * </p>
 *
 * @author jianghr
 * @since 2023-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cqwu_user_diary")
@ApiModel(value = "UserDiary对象", description = "")
public class UserDiaryEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7792624981149079856L;

    @ApiModelProperty("日记标题")
    private String title;

    @ApiModelProperty("日记内容")
    private String content;

    @ApiModelProperty("html格式日记内容")
    private String htmlContent;

    @ApiModelProperty("天气")
    private String weather;

    @ApiModelProperty("心情")
    private String mood;
}
