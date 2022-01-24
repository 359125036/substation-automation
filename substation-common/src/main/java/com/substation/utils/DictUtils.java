package com.substation.utils;


import com.alibaba.fastjson.JSON;
import com.substation.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;


/**
 * 字典工具类
 *
 */
public class DictUtils
{
    /**
     * 设置字典缓存
     * 
     * @param key 参数键
     * @param dictDatas 字典数据列表
     */
    public  static <T> void setDictCache(String key, List<T> dictDatas)
    {
        SpringUtils.getBean(RedisOperator.class).hset(getCacheKey(key),"dict", JSON.toJSON(dictDatas).toString());
    }

    /**
     * 获取字典缓存
     * 
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static <T> List<T> getDictCache(String key)
    {
        String cacheObj = SpringUtils.getBean(RedisOperator.class).hget(getCacheKey(key),"dict");
        if (StringUtils.isNotBlank(" "+cacheObj))
        {
            List<T> DictDatas =(List<T>)JSON.parseArray(cacheObj);
            return DictDatas;
        }
        return null;
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisOperator.class).keys(Constants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisOperator.class).deleteObject(keys);
    }

    /**
     * 设置cache key
     * 
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey)
    {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
