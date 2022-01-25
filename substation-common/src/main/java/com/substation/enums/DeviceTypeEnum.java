package com.substation.enums;

/**
 * @ClassName: DeviceTypeEnum
 * @Author: zhengxin
 * @Description: 设备类型枚举
 * @Date: 2020/12/14 10:52
 * @Version: 1.0
 */
public enum DeviceTypeEnum {

    HK("0","海康威视"),
    OTHER("-1","其他");

    public final String type;

    public final String value;

    DeviceTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
