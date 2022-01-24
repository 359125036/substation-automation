package com.substation.utils;

/**
 * @ClassName: CrcUtil
 * @Author: zhengxin
 * @Description: CRC校验
 * @Date: 2020/12/1 8:59
 * @Version: 1.0
 */
public class CrcUtil {

    /**
     * 计算产生校验码
     *
     * @param data
     *            需要校验的数据
     * @return
     */
    public static String Make_CRC(byte[] data) {
        byte[] buf = new byte[data.length];// 存储需要产生校验码的数据
        for (int i = 0; i < data.length; i++) {
            buf[i] = data[i];
        }
        int len = buf.length;
        int crc = 0xFFFF;
        for (int pos = 0; pos < len; pos++) {
            if (buf[pos] < 0) {
                crc ^= (int) buf[pos] + 256; // XOR byte into least sig. byte of
                // crc
            } else {
                crc ^= (int) buf[pos]; // XOR byte into least sig. byte of crc
            }
            for (int i = 8; i != 0; i--) { // Loop over each bit
                if ((crc & 0x0001) != 0) { // If the LSB is set
                    crc >>= 1; // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                } else
                    // Else LSB is not set
                    crc >>= 1; // Just shift right
            }
        }
        String c = Integer.toHexString(crc);
        if (c.length() == 4) {
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 3) {
            c = "0" + c;
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 2) {
            c = "0" + c.substring(1, 2) + "0" + c.substring(0, 1);
        }
        return c;
    }

    /**
     * @Method tcpParam
     * @Author zhengxin
     * @Description 请求tcp服务端参数
     * @Param: param 55 寄存器地址 03 命令 00 00 相电压Uan 00 38 总有功功率P
     * @Return byte[]
     * @Date 2020/12/1 9:05
     * @Version  1.0
     */
    public static byte[] tcpParam(String param){
        //参数转字节
        byte[] byteParam = StringToHexUtil.hexStrToByteArrs(param);
        //生成 CRC校验码
        String crc = Make_CRC(byteParam);
        //生成 请求tcp参数
        String tcpParam=param+crc;
        return StringToHexUtil.hexStrToByteArrs(tcpParam);
    }

}
