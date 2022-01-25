package com.substation.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ObjectToMapUtil
 * @Author: zhengxin
 * @Description: 实体转map
 * @Date: 2021/3/30 9:35
 * @Version: 1.0
 */
public class ObjectToMapUtil {

    public static Map<String, String> convert(Object object) throws Exception {
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String value = field.get(object) != null ? field.get(object).toString() : "";
            map.put(field.getName(), value);
        }
        return map;
    }
}
