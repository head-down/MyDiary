package cqwu.edu.diary.common.exception;

/**
 * 自定义运行异常
 *
 * @author Ethan
 * @version V1.0
 * @email wangmin1@mochasoft.com.cn
 * @since 2022/11/11 17:42
 */
public class CustomRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 154141025257886487L;

    //  状态码
    private final Integer code;
    //  错误描述
    private final String msg;

    public CustomRuntimeException(final Integer code, final String msg, final Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }

    public CustomRuntimeException(final Integer code, final String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "[ERRORCODE=" +
                getCode() +
                "] [" +
                (msg == null ? "" : msg) + "]";
    }
}
