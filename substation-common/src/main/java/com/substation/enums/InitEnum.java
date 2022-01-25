package com.substation.enums;

/**
 * @ClassName: InitEnum
 * @Author: zhengxin
 * @Description: 初始化数据枚举
 * @Date: 2021/6/2 14:43
 * @Version: 1.0
 */
public enum InitEnum {

    PUBLIC_IP("公网ip","127.0.0.1","sys.publicIp"),
    WEB_PORT("web项目端口","8089","sys.webPort"),
    STREAM_SERVER_PORT("视频流服务器端口号","8080","sys.streamServerPort");

    public final String value;

    public final String defaultkey;

    public final String key;

    InitEnum(String value, String defaultkey, String key) {
        this.value = value;
        this.defaultkey = defaultkey;
        this.key = key;
    }
}
