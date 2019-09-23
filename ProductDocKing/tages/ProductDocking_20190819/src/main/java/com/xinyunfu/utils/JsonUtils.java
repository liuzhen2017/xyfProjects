package com.xinyunfu.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

/**
 * @author XRZ
 * @date 2019/7/6 0006
 * @Description :
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    /**
     *  对象转JSON
     * @param obj
     * @return
     */
    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    /**
     *   JSON 转换对象
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }


    /**
     *  JSON 转 对象list
     * @param json
     * @param eClass
     * @param <E>
     * @return
     */
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * JSON 转 map
     * @param json
     * @param kClass
     * @param vClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     *  json 转 复杂类型 (使用泛型+匿名内部类实现)
     *      例：List<Map<String, Object>> json = nativeRead("json", new TypeReference<List<Map<String, Object>>>() {});
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

}
