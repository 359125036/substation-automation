package com.substation.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @ClassName: Base64ConvertUtil
 * @Author: zhengxin
 * @Description: Base64工具
 * @Date: 2020/11/11 13:50
 * @Version: 1.0
 */
public class Base64ConvertUtil {

    private Base64ConvertUtil() {}

    /**
     * @Method encode
     * @Author zhengxin
     * @Description 加密JDK1.8
     * @param str
     * @Return java.lang.String
     * @Exception
     * @Date 2020/11/11 13:50
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    /**
     * @Method decode
     * @Author zhengxin
     * @Description 解密JDK1.8
     * @param str
     * @Return java.lang.String
     * @Exception
     * @Date 2020/11/11 13:51
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }

}
