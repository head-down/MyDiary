package cqwu.edu.diary.common.exception;

import cqwu.edu.diary.common.response.CustomReturn;
import cqwu.edu.diary.common.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获对象
 */
@RestController
public class GlobalErrorController extends BasicErrorController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorController.class);
    /**
     * 字符串常量response
     */
    private static final String CONSTANTS_RESULT = "response";

    /**
     * 无参构造器
     */
    public GlobalErrorController() {
        super(new DefaultErrorAttributes(),new ErrorProperties());
    }


    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        HttpStatus status = this.getStatus(request);
        Map<String, Object> errorObj = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
        if (errorObj == null) {
            return new ResponseEntity<Map<String, Object>>(response, status);
        }
        final Object exObj = request.getAttribute("javax.servlet.error.exception");
        LOGGER.info("exObj class:{}",exObj.getClass());
        // 根据情况error不同，故不加final
        CustomReturn<Object> error;
        if (exObj instanceof BusinessException) {
            LOGGER.error("BusinessException msg：{}", JsonUtil.data2Json(errorObj));
            BusinessException mesException = (BusinessException) exObj;
            //flag设置为空进行跳转
            error = CustomReturn.error(mesException.getMessage(), mesException.getCode());
            status = HttpStatus.OK;
        }else{
            LOGGER.error("global exception msg：{}", JsonUtil.data2Json(errorObj));
            error = CustomReturn.error("系统异常");
            error.setCode(status.value());
        }
        response.put(CONSTANTS_RESULT, error);
        return new ResponseEntity<Map<String, Object>>(response, status);
    }
}
