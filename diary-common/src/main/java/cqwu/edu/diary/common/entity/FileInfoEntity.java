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
 * 
 * </p>
 *
 * @author jianghr
 * @since 2023-01-28
 */
@Data
@TableName("diary_file_info")
@ApiModel(value = "FileInfo对象", description = "")
@EqualsAndHashCode(callSuper = true)
public class FileInfoEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件目录")
    private String path;

    @ApiModelProperty("关联id")
    private Long referenceId;

    @ApiModelProperty("业务类型:1、头像")
    private Integer businessType;

    @ApiModelProperty("删除标识")
    private Integer delFlag;

    @ApiModelProperty("创建人id")
    private Long createUserId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改人id")
    private Long modifyUserId;

    @ApiModelProperty("修改时间")
    private LocalDateTime modifyTime;
}
