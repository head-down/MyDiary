package cqwu.edu.diary.common.utils;

import cqwu.edu.diary.common.enumerate.CustomMessageEnum;
import cqwu.edu.diary.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class FileUtil {

    /**
     * 上传
     *
     * @param file 文件名
     *
     */
    public static boolean upload(final MultipartFile file, final String url) {

        try {
            //文件目录
            final File dest = new File(url);
            // 检测是否存在目录
            if (!dest.getParentFile()
                .exists()) {
                final boolean mkdirs = dest.getParentFile()
                    .mkdirs();
                log.info("文件夹创建：" + mkdirs);
            }
            //保存文件
            file.transferTo(dest);
        } catch (final IOException e) {
            log.error("上传文件报错,file:{},url:{}",file,url,e);
            return false;
        }
        return true;
    }

    /**
     * 下载
     *
     * @param filePath     真实文件路径
     * @param response     响应
     */
    public static void download(final String filePath, final HttpServletResponse response) {

            // path是指欲下载的文件的路径。
            final File file = new File(Optional.ofNullable(filePath).orElse(""));
            log.info("获取{}", filePath);
            if (!file.exists()) {
                log.error("文件不存在,filePath:{}", filePath);
                throw new BusinessException(CustomMessageEnum.FILE_NOT_EXIST_ERROR);
            }

            // 取得文件名。
            final String filename = file.getName();
            log.info("取得文件名{}", filename);

            try{
                FileUtils.copyFile(file,response.getOutputStream());
            }catch(final IOException exception){
                log.error("下载文件错误", exception);
                throw new BusinessException(CustomMessageEnum.FILE_DOWNLOAD_ERROR, exception);
            }
    }
}