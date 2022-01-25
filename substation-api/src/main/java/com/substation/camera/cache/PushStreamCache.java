package com.substation.camera.cache;

import com.substation.camera.push.CameraPush;
import com.substation.entity.Camera;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: PushStreamCache
 * @Author: zhengxin
 * @Description: 推流缓存信息
 * @Date: 2021/3/24 14:04
 * @Version: 1.0
 */
public final class PushStreamCache {

    /*
     * 保存已经开始推的流
     */
    public static Map<String, Camera> STREATMAP = new ConcurrentHashMap<String, Camera>();

    /*
     * 保存push
     */
    public static Map<String, CameraPush> PUSHMAP = new ConcurrentHashMap<>();
    /*
     * 保存服务启动时间
     */
    public static long STARTTIME;
}
