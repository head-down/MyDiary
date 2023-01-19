package cqwu.edu.diary.common.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ServiceResponseCode {

    private ServiceResponseCode() {

    }

    /**
     * 成功
     */
    public static final int OK = 200;
    /**
     * 未登录
     */
    public static final int NOT_LOGIN = 401;
    /**
     * 账号或密码错误
     */
    public static final int ACCOUNT_OR_PASSWORD_ERROR = 402;
    /**
     * 无创建操作权限
     */
    public static final int NOT_CREATE_PERMISSION = 403;
    /**
     * 未找到文档
     */
    public static final int NOT_FOUND_DOCUMENT = 404;
    /**
     * 查询数据为空
     */
    public static final int EMPTY_QUERY_PARAMETER = 408;
    /**
     * 请求参数错误.
     */
    public static final int ERROR_REQUEST_PARAMETER = 409;
    /**
     * 找不到服务
     */
    public static final int NOT_FOUND_SERVICE = 410;
    /**
     * 无删除文件权限
     */
    public static final int NOT_DELETE_FILE_PERMISSION = 413;
    /**
     * 无下载文件权限
     */
    public static final int NOT_DOWNLOAD_FILES_PERMISSION = 418;
    /**
     * 无修改权限
     */
    public static final int NOT_MODIFY_PERMISSIOIN = 420;
    /**
     * 无查看记录权限
     */
    public static final int NOT_VIEW_RECORD_PERMISSION = 424;
    /**
     * 重复提交
     */
    public static final int REPEAT_SUBMIT = 427;
    /**
     * 服务异常，只能用在全局异常捕获，其它地方禁止使用
     */
    public static final int SERVICE_ERROR = 500;
    /**
     * 服务内部异常，非接口调用异常，内部使用不暴露给用户
     */
    public static final int SERVICE_INNER_ERROR = 501;
    /**
     * 服务接口调用异常
     */
    public static final int SERVICE_INTERFACE_ERROR = 502;
    /**
     * 业务异常
     */
    public static final int BUSINESS_EXCEPTION = 201;

    /**
     * 状态码与中文含义对应MAP
     */
    public static final Map<Integer, String> STATUS_CODE_MAP;

    static {
        final HashMap<Integer, String> map = new HashMap<>();
        map.put(OK, "成功");
        map.put(NOT_LOGIN, "未登录");
        map.put(ACCOUNT_OR_PASSWORD_ERROR, "账号或密码错误");
        map.put(NOT_CREATE_PERMISSION, "无创建操作权限");
        map.put(NOT_FOUND_DOCUMENT, "未找到文档");
        map.put(EMPTY_QUERY_PARAMETER, "查询数据为空");
        map.put(NOT_FOUND_SERVICE, "找不到服务");
        map.put(NOT_DELETE_FILE_PERMISSION, "无删除文件权限");
        map.put(NOT_DOWNLOAD_FILES_PERMISSION, "无下载文件权限.");
        map.put(NOT_MODIFY_PERMISSIOIN, "无修改权限.");
        map.put(SERVICE_ERROR, "服务器异常.");
        map.put(SERVICE_INNER_ERROR, "服务器内部异常.");
        map.put(SERVICE_INTERFACE_ERROR, "服务器接口调用异常.");
        map.put(BUSINESS_EXCEPTION, "业务异常.");
        map.put(NOT_VIEW_RECORD_PERMISSION, "无查看记录权限.");
        STATUS_CODE_MAP = Collections.unmodifiableMap(map);
    }
}
