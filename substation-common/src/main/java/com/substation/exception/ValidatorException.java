package com.substation.exception;

/**
 * @ClassName: ValidatorException
 * @Author: zhengxin
 * @Description: 校验异常
 * @Date: 2020/10/13 15:26
 * @Version: 1.0
 */
public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);
    }
}
