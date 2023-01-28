package cqwu.edu.diary.common.response;


import cqwu.edu.diary.common.constants.ServiceResponseCode;
import cqwu.edu.diary.common.enumerate.CustomMessageEnum;
import lombok.Data;

import java.util.List;

@Data
public class CustomReturn<T> {

    /**
     * 返回状态码
     */
    private int code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 返回数据
     */
     private T data;
    /**
     * 如可处理的异常后返回的错误信息的对象
     */
    private Object errMsgObject;
    /**
     * 扩展字段
     */
    private List<Object> extProp;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CustomReturn<T> other = (CustomReturn<T>) obj;
        if (code != other.code) {
            return false;
        }
        if (message == null) {
            return other.message == null;
        } else {
            return message.equals(other.message);
        }
    }

    public static <T> CustomReturn<T> error(String errorMessage) {
        CustomReturn<T> customReturn = new CustomReturn<T>();
        customReturn.setMessage(errorMessage);
        return customReturn;
    }

    public static <T> CustomReturn<T> error(String errorMessage, int code) {
        CustomReturn<T> customReturn = new CustomReturn<T>();
        customReturn.setMessage(errorMessage);
        customReturn.setCode(code);
        return customReturn;
    }

    public static <T> CustomReturn<T> error(CustomMessageEnum messageEnum) {
        CustomReturn<T> customReturn = new CustomReturn<T>();
        customReturn.setMessage(messageEnum.getMsg());
        customReturn.setCode(messageEnum.getCode());
        return customReturn;
    }

    public static <T> CustomReturn<T> error(int code) {
        CustomReturn<T> customReturn = new CustomReturn<T>();
        final String msg = ServiceResponseCode.STATUS_CODE_MAP.get(code);
        customReturn.setMessage(msg);
        customReturn.setCode(code);
        return customReturn;
    }

    public static <T> CustomReturn<T> success(T data) {
        CustomReturn<T> customReturn = new CustomReturn<>();
        final String msg = ServiceResponseCode.STATUS_CODE_MAP.get(ServiceResponseCode.OK);
        customReturn.setCode(ServiceResponseCode.OK);
        customReturn.setMessage(msg);
        customReturn.setData(data);
        return customReturn;
    }

    public static <T> CustomReturn<T> success(String message) {
        CustomReturn<T> customReturn = new CustomReturn<>();
        customReturn.setMessage(message);
        customReturn.setCode(ServiceResponseCode.OK);
        return customReturn;
    }

    public static <T> CustomReturn<T> success(String message,T data) {
        CustomReturn<T> customReturn = new CustomReturn<>();
        customReturn.setMessage(message);
        customReturn.setCode(ServiceResponseCode.OK);
        customReturn.setData(data);
        return customReturn;
    }
}
