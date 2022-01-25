package com.substation.camera.thread;

import com.substation.camera.cache.PushStreamCache;
import com.substation.camera.push.CameraPush;
import com.substation.controller.camera.CameraController;
import com.substation.entity.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: CameraThread
 * @Author: zhengxin
 * @Description: 摄像头线程
 * @Date: 2021/3/24 14:11
 * @Version: 1.0
 */
public class CameraThread {

    private final static Logger logger = LoggerFactory.getLogger(CameraThread.class);

    public static class CameraRunnable implements Runnable{
        //创建线程池
        public static ExecutorService es= Executors.newCachedThreadPool();

        private Camera camera;

        private Thread nowThread;

        public CameraRunnable(Camera camera) {
            this.camera = camera;
        }

        //中断线程
        public void setInterrupted(String key){
            PushStreamCache.PUSHMAP.get(key).setExitcode(1);
        }

        @Override
        public void run() {
            // 直播流
            try {
                // 获取当前线程存入缓存
                nowThread = Thread.currentThread();
                PushStreamCache.STREATMAP.put(camera.getToken(), camera);
                // 执行转流推流任务
                CameraPush push = new CameraPush(camera);
                PushStreamCache.PUSHMAP.put(camera.getToken(), push);
                push.push();
                // 清除缓存
                PushStreamCache.STREATMAP.remove(camera.getToken());
                CameraController.JOBMAP.remove(camera.getToken());
                PushStreamCache.PUSHMAP.remove(camera.getToken());
            } catch (Exception e) {
                PushStreamCache.STREATMAP.remove(camera.getToken());
                CameraController.JOBMAP.remove(camera.getToken());
                PushStreamCache.PUSHMAP.remove(camera.getToken());
            }
        }
    }
}
