package com.substation.enums;

/**
 * ClassName:    DelFlagEnum
 * Package:    com.yddl.enums
 * Description: 删除标志
 * Datetime:    2020/11/24   17:56
 * Author:   zx
 */
public enum DelFlagEnum {

    EXIST_FLAG_ENUM("0","存在"),
    DEL_FLAG_ENUM("2","删除");

    public final String type;
    public final String value;

    DelFlagEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
