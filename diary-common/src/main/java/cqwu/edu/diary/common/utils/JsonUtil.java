package cqwu.edu.diary.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
public class JsonUtil{

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {

    }

    @Resource
    private ObjectMapper customObjectMapper;



    private static ObjectMapper objectMapper;

    @PostConstruct
    private void init(){
        LOGGER.info("customObjectMapper:{}",customObjectMapper);
        //通过postConstruct方式对static实现注入
        objectMapper = customObjectMapper;
    }

    /**
     * Object转换成json对象
     *
     * @param data
     * @return java.lang.String
     */
    public static String data2Json(Object data) {

        return data2Json(data, true);
    }

    /**
     * Object转换成json对象
     *
     * @param data
     * @param isEnable 是否包含null
     * @return java.lang.String
     */
    public static String data2Json(Object data, boolean isEnable) {
        LOGGER.info("objectMapper:{}",objectMapper);
        objectMapper.registerModule(new JavaTimeModule());

        if (isEnable) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        StringWriter stringWriter = new StringWriter();
        try {
            objectMapper.writeValue(stringWriter, data);
        } catch (IOException e) {
            LOGGER.error("程序出现了一个异常:{}", e.getMessage(), e);
            throw new RuntimeException("IOException from a StringWriter!");
        }
        return stringWriter.toString();
    }

    /**
     * json对象转换成指定的class对象
     *
     * @param data
     * @param valueClass
     * @param <V>
     * @return V
     */
    public static <V> V json2Object(String data, Class<V> valueClass) {

        return json2Object(data, valueClass, false);
    }

    /**
     * json对象转换成指定的class对象
     *
     * @param data
     * @param valueClass
     * @param fullMap
     * @param <V>
     * @return V
     */
    public static <V> V json2Object(String data, Class<V> valueClass, boolean fullMap) {

        if (!fullMap) {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        try {
            return objectMapper.readValue(data, valueClass);
        } catch (JsonProcessingException e) {
            LOGGER.error("程序出现了一个异常:{}", e.getMessage(), e);
            throw new RuntimeException("JsonProcessingException from json2Object method!");
        }
    }

    /**
     * @param data
     * @param typeReference
     * @param fillMap
     * @return java.lang.Object
     */
    public static Object setJson2Object(String data, TypeReference<?> typeReference, boolean fillMap) {

        if (!fillMap) {
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        try {
            return objectMapper.readValue(data, typeReference);
        } catch (JsonProcessingException e) {
            LOGGER.error("程序出现了一个异常:{}", e.getMessage(), e);
            throw new RuntimeException("JsonProcessingException from setJson2Object method!");
        }
    }

    /**
     * json字符串对象转换成Map对象
     *
     * @param data
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String data) {

        try {
            return objectMapper.readValue(data, Map.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("程序出现了一个异常:{}", e.getMessage(), e);
            throw new RuntimeException("JsonProcessingException from json2Map method!");
        }
    }


}
