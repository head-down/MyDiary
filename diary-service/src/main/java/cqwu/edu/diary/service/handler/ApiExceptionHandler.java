package cqwu.edu.diary.service.handler;

import cqwu.edu.diary.common.constants.ServiceConstants;
import cqwu.edu.diary.common.enumerate.CustomMessageEnum;
import cqwu.edu.diary.common.exception.BusinessException;
import cqwu.edu.diary.common.response.CustomReturn;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.naming.SizeLimitExceededException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public CustomReturn<Void> handleMultipartException(MaxUploadSizeExceededException ex) {
        LOGGER.info("cause:{}", ex.getCause().getCause().getClass());
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
        return CustomReturn.error(ex.getMessage(), ex.getCode());
    }

    /**
     * 参数校验全局异常处理
     *
     * @param e Exception
     * @return JsonReturn
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CustomReturn<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final String msg = "参数校验异常！";
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        final String errorMsg = msg + fieldErrors.stream()
                .map(error -> error.getField() + error.getDefaultMessage())
                .collect(Collectors.joining(";"));
        LOGGER.error("errorMsg:{}",errorMsg, e);
        return CustomReturn.error(errorMsg);
    }

    /**
     * 参数校验全局异常处理
     *
     * @param e Exception
     * @return JsonReturn
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CustomReturn<Void> handleConstraintViolationException(ConstraintViolationException e) {
        final String msg = "参数校验异常！";
        final String message = e.getMessage();
        final String errorMsg = msg + message;
        LOGGER.error("errorMsg:{}",errorMsg, e);
        return CustomReturn.error(errorMsg);
    }


/*    *//**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     *//*

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomReturn<Void> handleParameterException(MethodArgumentNotValidException e) {
        LOGGER.error("", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return CustomReturn.error(fieldError.getDefaultMessage(),CustomMessageEnum.PARAMETER_VALID_ERROR.getCode());
            }
        }
        return CustomReturn.error(CustomMessageEnum.PARAMETER_VALID_ERROR);
    }*/
}