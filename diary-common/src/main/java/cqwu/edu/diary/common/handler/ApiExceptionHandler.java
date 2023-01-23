package cqwu.edu.diary.common.handler;

import cqwu.edu.diary.common.exception.BusinessException;
import cqwu.edu.diary.common.response.CustomReturn;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.naming.SizeLimitExceededException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public CustomReturn<Void> handleMultipartException(MaxUploadSizeExceededException ex) {
        String msg;
        if (ex.getCause().getCause() instanceof SizeLimitExceededException) {
            LOGGER.error(ex.getMessage());
            msg = "上传文件过大[单文件大小不得超过50M]";
        } else if (ex.getCause().getCause() instanceof FileSizeLimitExceededException) {
            LOGGER.error(ex.getMessage());
            msg = "上传文件过大[总上传文件大小不得超过100M]";
        } else {
            msg = "上传文件失败";
        }
        return CustomReturn.error(msg);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CustomReturn<Void> handleBusinessException(BusinessException ex) {
        return CustomReturn.error(ex.getMessage(),ex.getCode());
    }

    /**
     * 参数校验全局异常处理
     *
     * @param e Exception
     * @return JsonReturn
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CustomReturn<Void> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final String msg = "参数校验异常！";
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        final String errorMsg = msg + fieldErrors.stream()
                .map(error -> error.getField() + error.getDefaultMessage())
                .collect(Collectors.joining(";"));
        LOGGER.error(errorMsg, e);
        return CustomReturn.error(errorMsg);
    }
}