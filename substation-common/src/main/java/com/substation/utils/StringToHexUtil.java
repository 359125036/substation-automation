package com.substation.utils;

import org.springframework.util.StringUtils;

import java.math.BigInteger;

/**
 * @ClassName: StringToHexUtil
 * @Author: zhengxin
 * @Description: 字符串和十六进制转换
 * @Date: 2020/12/1 9:08
 * @Version: 1.0
 */
public class StringToHexUtil {

    /**
     * 将十六进制的字符串转换成字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStrToByteArrs(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }

        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;

        byte[] bytes = new byte[len / 2];

        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);
            index += 2;
        }

        return bytes;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @param bArray
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    //将16进制字符串转换位10进制数字
    public static int decodeHEX(String hexs){
        BigInteger bigint=new BigInteger(hexs, 16);
        int numb=bigint.intValue();
        return numb;
    }
}
