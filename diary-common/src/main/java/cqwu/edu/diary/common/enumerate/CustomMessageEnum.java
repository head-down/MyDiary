package cqwu.edu.diary.common.enumerate;


public enum CustomMessageEnum {
    /**
     * 流程错误以1开头
     */
    //流程发起失败
    PROCESS_START_ERROR(1001,"流程发起失败"),
    //流程提交失败
    PROCESS_SUBMIT_ERROR(1002,"流程提交失败"),
    // 流程回退失败
    PROCESS_REJECT_ERROR(1003,"流程回退失败"),
    //查询流程模板失败
    PROCESS_TEMPLATE_QUERY_ERROR(1004,"查询流程模板失败"),
    //查询当前任务节点失败
    PROCESS_CURRENT_QUERY_ERROR(1005,"查询当前任务节点失败"),
    /**
     * 栏目相关错误以2开头
     */
    //栏目保存失败
    COLUMN_SAVE_ERROR(2001,"栏目保存失败"),

    //栏目删除失败
    COLUMN_DELETE_ERROR(2002,"栏目删除失败"),

    //栏目保存失败
    COLUMN_UPDATE_ERROR(2003,"栏目更新失败"),

    //栏目未找到
    COLUMN_NOT_FOUND(2004,"栏目未找到"),
    /**
     * 栏目下还存在工作
     */
    COLUMN_HAS_WORK(2005,"栏目下还存在工作"),

    /**
     * 文件相关错误以3开头
     */
    //文件下载失败
    FILE_DOWNLOAD_ERROR(3001,"文件下载失败"),

    //下载文件不存在
    FILE_NOT_EXIST_ERROR(3002,"文件不存在"),

    //文件上传失败
    FILE_UPLOAD_ERROR(3003,"文件上传失败"),

    /**
     * 工作相关错误以4开头
     */
    //工作删除失败
    WORK_DELETE_ERROR(4001,"工作删除失败"),
    //工作未找到
    WORK_NOT_FOUND(4002,"工作未找到"),
    //工作保存失败
    WORK_SAVE_ERROR(4003,"工作保存失败"),
    //工作更新失败
    WORK_UPDATE_ERROR(4004,"工作更新失败"),
    //工作得票数据保存失败
    WORK_TICKET_SAVE_ERROR(4005,"工作得票数据保存失败"),

    /**
     * 流程相关错误以5开头
     */
    //节点查询失败
    NODE_QUERY_ERROR(5001,"节点查询失败"),

    //---- 票数相关错以6开头 ----//
    /**
     * 票数不足错误
     */
    SHORT_OF_VOTES_ERROR(6001,"剩余票数不足!"),

    /**
     * 分票异常
     */
    DISTRIBUTE_TICKET_ERROR(6002,"分票异常！"),

    /**
     * 未查询到用户余票信息
     */
    USER_TICKET_INFO_NOT_FOUND_ERROR(6003,"未查询到用户余票信息"),

    //---- 系统相关错以7开头 ----//
    /**
     * 单点登录异常
     */
    SSO_ERROR(7001,"单点登录异常"),

    //---- 定时任务相关错以8开头 ----//
    /**
     * 任务不存在
     */
    TASK_NOT_EXIST(8001,"任务不存在"),
    ;
    private final Integer code;
    private final String msg;

    CustomMessageEnum(Integer code,String msg){
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
