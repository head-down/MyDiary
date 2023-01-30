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
@TableName("cqwu_file_info")
@ApiModel(value = "FileInfo对象", description = "")
@EqualsAndHashCode(callSuper = true)
public class FileInfoEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -8449557208100859218L;

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
}
