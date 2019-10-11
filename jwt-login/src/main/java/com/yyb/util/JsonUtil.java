package com.yyb.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * bean转json格式或者json转bean格式
 */
public class JsonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * 对象转换为json字符串
     */
    public static String obj2json(Object obj) throws BussinessException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new BussinessException("对象转换为json字符串出错，" + e.getMessage());
        }
    }

    /**
     * javaBean、列表数组转换为json字符串,忽略空值
     */
    public static String obj2jsonIgnoreNull(Object obj) throws BussinessException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new BussinessException("javaBean、列表数组转换为json字符串出错，" + e.getMessage());
        }

    }

    /**
     * json 转JavaBean
     */

    public static <T> T json2obj(String jsonString, Class<T> clazz) throws BussinessException {
        //接受强制非数组（JSON）值到Java集合类型。如果允许，集合反序列化将尝试处理非数组值。
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new BussinessException("json转JavaBean出错，" + e.getMessage());
        }
    }


    /**
     * json字符串转换为map
     */
    public static <T> Map<String, T> json2map(String jsonString, Class<T> clazz) throws BussinessException {
        try {
            Map<String, Map<String, Object>> map = objectMapper.readValue(jsonString, new TypeReference<Map<String, T>>() {
            });
            Map<String, T> result = new HashMap<String, T>();
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), objectMapper.convertValue(entry.getValue(), clazz));
            }
            return result;
        } catch (IOException e) {
            throw new BussinessException("json字符串转换为map出错，" + e.getMessage());
        }

    }

    /**
     * json数组字符串转换为javaBean列表
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) throws BussinessException {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            List<T> lst = (List<T>) objectMapper.readValue(jsonArrayStr, javaType);
            return lst;
        } catch (IOException e) {
            throw new BussinessException("json数组字符串转换为javaBean列表出错，" + e.getMessage());
        }

    }

}