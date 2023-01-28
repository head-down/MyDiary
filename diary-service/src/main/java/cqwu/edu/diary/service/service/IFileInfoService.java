package cqwu.edu.diary.service.service;

import cqwu.edu.diary.common.entity.FileInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianghr
 * @since 2023-01-28
 */
public interface IFileInfoService extends IService<FileInfoEntity> {

    /**
     * 上传图片
     * @param file 图片文件
     * @return 上传是否成功
     */
    Boolean uploadImage(final MultipartFile file);
}
