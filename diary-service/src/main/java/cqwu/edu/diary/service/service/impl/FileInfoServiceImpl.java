package cqwu.edu.diary.service.service.impl;

import cqwu.edu.diary.common.constants.ServiceConstants;
import cqwu.edu.diary.common.entity.FileInfoEntity;
import cqwu.edu.diary.common.enumerate.CustomMessageEnum;
import cqwu.edu.diary.common.exception.BusinessException;
import cqwu.edu.diary.common.utils.FileUtil;
import cqwu.edu.diary.common.utils.SnowflakeDistributeId;
import cqwu.edu.diary.service.config.CommonConfig;
import cqwu.edu.diary.service.mapper.FileInfoMapper;
import cqwu.edu.diary.service.security.util.SecurityUtil;
import cqwu.edu.diary.service.service.IFileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>
 * 文件服务实现类
 * </p>
 *
 * @author jianghr
 * @since 2023-01-28
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfoEntity> implements IFileInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileInfoServiceImpl.class);

    // 头像大小限制
    private static final Long PROFILE_LIMIT = 2 * 1024 * 1024L;

    @Resource
    private FileInfoMapper fileInfoMapper;

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 上传是否成功
     */
    @Override
    public Boolean uploadImage(final MultipartFile file) {
        // 获取文件名
        final String fileName = file.getOriginalFilename();

        // 获取后缀
        final String extension = FilenameUtils.getExtension(fileName);
        // 头像校验
        checkProfile(file, fileName);

        // 解决中文问题，liunx下中文路径，图片显示问题
        final String newFileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + extension;

        LOGGER.info("文件新名称:{}", newFileName);
        // 上传
        final boolean isUploaded = FileUtil.upload(file, CommonConfig.FILE_PATH + File.separator + newFileName);

        // 上传成功，插入表数据
        if (isUploaded) {
            final FileInfoEntity fileInfoEntity = lambdaQuery()
                    .eq(FileInfoEntity::getReferenceId, SecurityUtil.getId())
                    .eq(FileInfoEntity::getBusinessType, ServiceConstants.FILE_PROFILE_TYPE)
                    .oneOpt()
                    .orElseGet(FileInfoEntity::new);
            fileInfoEntity.setBusinessType(ServiceConstants.FILE_PROFILE_TYPE);
            fileInfoEntity.setName(newFileName);
            fileInfoEntity.setReferenceId(SecurityUtil.getId());
            fileInfoEntity.setType(extension);
            fileInfoEntity.setPath("/profile" + File.separator + newFileName);
            // 如果存在就更新
            if (fileInfoEntity.getId() == null) {
                fileInfoEntity.setId(SnowflakeDistributeId.getInstance().nextId());
                fileInfoMapper.insert(fileInfoEntity);
            } else {
                fileInfoMapper.updateById(fileInfoEntity);
            }
        }
        return isUploaded;
    }

    /**
     * 校验头像文件格式大小
     *
     * @param file     图像文件
     * @param fileName 文件名
     */
    private void checkProfile(final MultipartFile file, String fileName) {


        if (!FilenameUtils.isExtension(fileName, ServiceConstants.PROFILE_SUFFIX_ARRAY)) {
            // 后缀非法
            LOGGER.error("后缀非法,fileName:{}", fileName);
            throw new BusinessException(CustomMessageEnum.FILE_EXTENSION_ERROR);
        }
        if (file.getSize() == 0 || file.getSize() > PROFILE_LIMIT) {
            // 文件大小非法
            LOGGER.error("文件大小非法,size:{}", file.getSize());
            throw new BusinessException(CustomMessageEnum.FILE_SIZE_ERROR);
        }
    }
}
