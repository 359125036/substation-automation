package com.substation.enums;

import com.substation.hk_sdk.HCNetSDK;

/**
 * @ClassName: DirectionEnum
 * @Author: zhengxin
 * @Description: 视频转向枚举
 * @Date: 2020/12/21 11:51
 * @Version: 1.0
 */
public enum DirectionEnum {

    LEFT_ENUM("LEFT", HCNetSDK.PAN_LEFT),
    RIGHT_ENUM("RIGHT",HCNetSDK.PAN_RIGHT),
    UP_ENUM("UP",HCNetSDK.TILT_UP),
    DOWN_ENUM("DOWN",HCNetSDK.TILT_DOWN);

    private final String direction;

    private final int value;

    DirectionEnum(String direction, int value) {
        this.direction = direction;
        this.value = value;
    }

    public static int getValue(String direction) {
        DirectionEnum[] directionEnums = values();
        for (DirectionEnum directionEnum : directionEnums) {
            if (directionEnum.direction.equals(direction)) {
                return directionEnum.value;
            }
        }
        return 0;
    }
}
