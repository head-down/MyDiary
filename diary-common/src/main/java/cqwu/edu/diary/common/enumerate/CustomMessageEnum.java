package cqwu.edu.diary.common.enumerate;


public enum CustomMessageEnum {

    /* 自定义异常以1开头 */
    CUSTOM_MESSAGE_ENUM(1001, "自定义异常"),

    /* 文件相关错误以3开头 */
    //文件下载失败
    FILE_DOWNLOAD_ERROR(3001, "文件下载失败"),

    //下载文件不存在
    FILE_NOT_EXIST_ERROR(3002, "文件不存在"),

    //文件上传失败
    FILE_UPLOAD_ERROR(3003, "文件上传失败"),
    ;

    private final Integer code;
    private final String msg;

    CustomMessageEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
