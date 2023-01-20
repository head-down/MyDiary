package cqwu.edu.diary.common.exception;


import cqwu.edu.diary.common.enumerate.CustomMessageEnum;

/**
 * 自定义业务异常
 *
 */
public class BusinessException extends CustomRuntimeException {

    public BusinessException(final CustomMessageEnum customMessageEnum){
        super(customMessageEnum.getCode(),customMessageEnum.getMsg());
    }

    public BusinessException(final CustomMessageEnum customMessageEnum,final Throwable th){
        super(customMessageEnum.getCode(),customMessageEnum.getMsg(),th);
    }
    public BusinessException(final Integer code, final String msg) {
        super(code, msg);
    }
}
