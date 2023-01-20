package cqwu.edu.diary.service.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import cqwu.edu.diary.service.security.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 启动自动填充功能
 *
 * @author Ethan
 * @version V1.0
 * @email wangmin1@mochasoft.com.cn
 * @since 2022/11/10 18:19
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createUserId",Integer.class,SecurityUtil.getId());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "modifyUserId", Integer.class, SecurityUtil.getId());

    }
}