package com.substation.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ValidationEmptyUtil
 * @Author: zhengxin
 * @Description: 验证参数是否空
 * @Date: 2021/3/24 16:00
 * @Version: 1.0
 */
public class ValidationEmptyUtil {

    /**
     * @Method isNullParameters
     * @Author zhengxin
     * @Description  验证json中是否存在空
     * @Param: [json 参数, isNullArr 需验证是否空的数组]
     * @Return boolean
     * @Date 2021/3/24 16:00
     * @Version  1.0
     */
    public static boolean isNullParameters(JSONObject json, String[] isNullArr) {
        Map<String, Object> checkMap = new HashMap<>();
        // 空值校验
        for (String key : isNullArr) {
            if (null == json.get(key) || "".equals(json.get(key))) {
                return false;
            }
        }
        return true;
    }
}
