package com.substation.http;

import com.alibaba.fastjson.JSONObject;
import com.substation.utils.CrcUtil;
import com.substation.utils.PowerDataAnalysisUtil;
import com.substation.utils.StringToHexUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @ClassName: TcpUtil
 * @Author: zhengxin
 * @Description: tcp公用类
 * @Date: 2020/11/30 17:21
 * @Version: 1.0
 */
public class TcpUtil {

    /**
     * @Method sendTCP
     * @Author zhengxin
     * @Description 发送tcp请求
     * @Param: param 请求参数
     * @Param: host ip
     * @Param: port 端口
     * @Return byte[]
     * @Date 2020/12/1 9:24
     * @Version 1.0
     */
    public static byte[] sendTCP(String param, String host, Integer port) {

        byte[] bs = CrcUtil.tcpParam(param);
        byte[] data = null;
        InputStream in = null;
        OutputStream out = null;
        Socket socket = null;
        //创建Socket对象，连接服务器
        try {
            socket = new Socket(host, port);
            //通过客户端的套接字对象Socket方法，获取字节输出流，将数据写向服务器
            out = socket.getOutputStream();
            out.write(bs);
            //读取服务器发回的数据，使用socket套接字对象中的字节输入流
            in = socket.getInputStream();
            data = new byte[1024];
            int len = in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return data;
    }

    /**
     * 将Map转为javaBean
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        if (map == null)
            return null;
        T obj = null;
        BeanInfo beanInfo = null;
        try {
            obj = beanClass.newInstance();
            beanInfo = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // 如果在创建实例和获取beaninfo出现异常则直接返回null
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                String key = property.getName();
                try {
                    setter.invoke(obj, map.get(key));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                }
            }
        }
        return obj;
    }

    public static void main(String[] args) throws IOException {
        String string = "55 03 00 00 00 38";
        byte[] bytes = sendTCP(string, "192.168.137.4", 502);
        String string1 = StringToHexUtil.bytesToHexString(bytes);
        System.out.println(string1);
        Map<String, Object> map = PowerDataAnalysisUtil.powerDataAnalysis(string1);
//        mapToObject(map,ElectricCapacity.clas);
        JSONObject jsonObj = new JSONObject(map);
//        ElectricCapacity electricCapacity =JSONObject.toJavaObject(jsonObj, ElectricCapacity.class);
        System.out.println(string1.substring(4, 8));
////        byte[] bs = { 85, 3, 0, 0, 0, 56, 73, -52, 13, 10 };
////        byte[] crc={85, 3, 0, 0, 0, 56};
////        String s = Make_CRC(crc);
////        System.out.println(s);
////        System.out.println(Arrays.toString(hexStrToByteArrs(string)));
////        System.out.println(bytesToHexString(bs));
//        byte[] bs = CrcUtil.tcpParam(string);
////        System.out.println(bytesToHexString(bs));
//        //创建Socket对象，连接服务器
//        Socket socket = new Socket("192.168.137.4", 502);
//        //通过客户端的套接字对象Socket方法，获取字节输出流，将数据写向服务器
//        OutputStream out = socket.getOutputStream();
//        out.write(bs);
//
//        //读取服务器发回的数据，使用socket套接字对象中的字节输入流
//        InputStream in = socket.getInputStream();
//        byte[] data = new byte[1024];
//
//        int len = in.read(data);
////        while(len!=-1){
////            out.write(data,0,len);
////            }
//        //获取服务器的反馈信息
////        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
//        System.out.println(new String(data, 0, len));
////        String string1 = bytesToHexString(data);
////        System.out.println(string1);
//        in.close();
//        out.close();
//        socket.close();
    }


}
