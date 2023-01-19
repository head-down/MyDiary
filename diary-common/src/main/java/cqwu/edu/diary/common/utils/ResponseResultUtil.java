package cqwu.edu.diary.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应结果封装
 *
 */
@Slf4j
public class ResponseResultUtil {
    private ResponseResultUtil() {

    }

    /**
     * 使用response输出JSON
     *
     * @param response
     * @param resultMap
     */
    public static void responseJson(ServletResponse response, Map<String, Object> resultMap) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.println(JsonUtil.data2Json(resultMap));
        } catch (IOException ex) {
            log.error("JSON输出异常：{}", ex);
        }
    }

    /**
     * 返回成功
     */
    public static Map<String, Object> resultSuccess(Map<String, Object> resultMap) {
        resultMap.put("message", "操作成功");
        resultMap.put("code", 200);
        return resultMap;
    }

    /**
     * 返回失败
     *
     */
    public static Map<String, Object> resultError(Map<String, Object> resultMap) {
        resultMap.put("message", "操作失败");
        resultMap.put("code", 500);
        return resultMap;
    }

    /**
     * 自定义返回
     *
     */
    public static Map<String, Object> resultCode(Integer code, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", msg);
        resultMap.put("code", code);
        return resultMap;
    }
}
