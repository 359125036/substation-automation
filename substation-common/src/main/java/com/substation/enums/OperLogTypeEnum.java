package com.substation.enums;

/**
 * ClassName:    OperLogTypeEnum
 * Package:    com.yddl.enums
 * Description: 操作日志
 * Datetime:    2020/11/25   11:18
 * Author:   zx
 */
public enum OperLogTypeEnum {

    OTHER_ENUM(0,"其他"),
    INSERT_ENUM(1,"新增"),
    UPDATE_ENUM(2,"修改"),
    DELETE_ENUM(3,"删除"),
    EXPORT_ENUM(4,"导出"),
    LOGIN_ENUM(5,"登录");

    public final Integer type;
    public final String value;

    OperLogTypeEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
