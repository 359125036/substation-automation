package com.substation.utils;

import com.substation.enums.DirectionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: CameraOperationUtil
 * @Author: zhengxin
 * @Description: 摄像头操作类
 * @Date: 2021/3/30 9:27
 * @Version: 1.0
 */
public class CameraOperationUtil {

    private static final Logger log = LoggerFactory.getLogger(CameraOperationUtil.class);

    /**
     * @Method rotateCamera
     * @Author zhengxin
     * @Description 摄像头转动
     * @Param: [map 设备信息, direction 转向 ]
     * @Return boolean
     * @Date 2020/12/21 13:36
     * @Version  1.0
     */
    public static boolean rotateCamera(Map<String,String> map, String direction){
        return new HCNetUtils().rotateCamera(map.get("account"),map.get("password"),map.get("ip"),map.get("directionPort"), DirectionEnum.getValue(direction),2);
    }

    /**
     * @Method shop
     * @Author zhengxin
     * @Description 停止摄像头转动
     * @Param: [map 设备信息, direction 转向 ]
     * @Return boolean
     * @Date 2020/12/21 13:36
     * @Version  1.0
     */
    public static boolean shop(Map<String,String> map,String direction){
        return new HCNetUtils().shop(map.get("account"),map.get("password"),map.get("ip"),map.get("directionPort"), DirectionEnum.getValue(direction),1);
    }

    /**
     * @Method downloadVideo
     * @Author zhengxin
     * @Description 按时间下载视频
     * @Param: [map 设备信息]
     * @Return com.yddl.http.HttpResult
     * @Date 2021/3/31 10:38
     * @Version  1.0
     */
    public static void downloadVideo(Map<String,String> map){
        new HCNetUtils().downloadVideo(map);
    }

    /**
     * @Method capturePictures
     * @Author zhengxin
     * @Description 图片抓拍
     * @Param: [map 设备信息,图片保存位置]
     * @Return void
     * @Date 2021/4/30 9:09
     * @Version  1.0
     */
    public static void capturePictures(Map<String,String> map){
        //文件夹不存在就先创建
        FileUtils.createMkdir(FileUtils.getCataloguePath(ConstantUtil.PHOTO_CATALOGUE));
        new HCNetUtils().getDVRPic(map);
    }

//    public static void main(String[] args) {
//        Map<String,String> map=new HashMap<>();
//        map.put("ip","218.106.157.166");
//        map.put("dvrPort","8000");
//        map.put("password","ydyf2020");
//        map.put("account","admin");
//        map.put("channel","1");
//        map.put("imgPath","F:\\抓拍图片\\test.jpg");
//
//        capturePictures(map);
//    }
    public static class DownloadVideoRunnable implements Runnable{
        //创建线程池
        public static ExecutorService es= Executors.newCachedThreadPool();

        private Map<String,String> map;

        private Thread nowThread;

        public DownloadVideoRunnable(Map<String, String> map) {
            this.map = map;
        }

        @Override
        public void run() {
            // 直播流
            try {
                // 获取当前线程存入缓存
                nowThread = Thread.currentThread();
                new HCNetUtils().downloadVideo(map);
            } catch (Exception e) {

            }
        }
    }

}
