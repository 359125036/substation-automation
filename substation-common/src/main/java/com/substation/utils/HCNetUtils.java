package com.substation.utils;

import cc.eguid.FFmpegCommandManager.FFmpegManager;
import com.substation.hk_sdk.HCNetSDK;
import com.substation.http.HttpResult;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: HCNetUtils
 * @Author: zhengxin
 * @Description: 海康威视工具类
 * @Date: 2020/12/10 14:53
 * @Version: 1.0
 */
public class HCNetUtils {

    private final static Logger logger = LoggerFactory.getLogger(HCNetUtils.class);

    /*
     * 下载进度
     */
    public static Map<String, String> DOWNLOAD_MAP = new ConcurrentHashMap<String, String>();

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    HCNetSDK.NET_DVR_IPPARACFG  m_strIpparaCfg;//IP参数
    HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//用户参数

    boolean bRealPlay;//是否在预览.
    String m_sDeviceIP;//已登录设备的IP地址

    NativeLong lUserID;//用户句柄
    NativeLong lPreviewHandle;//预览句柄
    NativeLong loadHandle;//下载句柄
    NativeLongByReference m_lPort;//回调预览时播放库端口指针

    static int m_lPlayHandle = -1;

    FPlayDataCallBack playCallback = null;//回放回调函数实现

    FFmpegManager manager;//rstp转rmtp工具


    //FRealDataCallBack fRealDataCallBack;//预览回调函数实现

    public HCNetUtils()
    {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);//防止被播放窗口(AWT组件)覆盖
        lUserID = new NativeLong(-1);
        lPreviewHandle = new NativeLong(-1);
        m_lPort = new NativeLongByReference(new NativeLong(-1));
        //fRealDataCallBack= new FRealDataCallBack();
    }

    /**
     * 初始化资源配置
     */
    public int initDevices(){
        if(!hCNetSDK.NET_DVR_Init()) return 1;//初始化失败
        return 0;
    }
    /**
     * 设备注册
     * @param name 设备用户名
     * @param password 设备登录密码
     * @param ip IP地址
     * @param port 端口
     * @return 结果
     */
    public int deviceRegist(String name,String password,String ip,String port){
        if (bRealPlay){//判断当前是否在预览
            return 2;//"注册新用户请先停止当前预览";
        }
        if (lUserID.longValue() > -1){//先注销,在登录
            hCNetSDK.NET_DVR_Logout_V30(lUserID);
            lUserID = new NativeLong(-1);
        }
        //注册(既登录设备)开始
        m_sDeviceIP = ip;
        m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();//获取设备参数结构
        lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,(short)Integer.parseInt(port),name,password, m_strDeviceInfo);//登录设备
        long userID = lUserID.longValue();
        if (userID == -1){
            m_sDeviceIP = "";//登录未成功,IP置为空
            return 3;//"注册失败";
        }
        return 0;
    }

    /**
     * 获取设备通道
     */
    public int getChannelNumber(){
        IntByReference ibrBytesReturned = new IntByReference(0);//获取IP接入配置参数
        boolean bRet = false;
        int iChannelNum = -1;

        m_strIpparaCfg = new HCNetSDK.NET_DVR_IPPARACFG();
        m_strIpparaCfg.write();
        Pointer lpIpParaConfig = m_strIpparaCfg.getPointer();
        bRet = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0), lpIpParaConfig, m_strIpparaCfg.size(), ibrBytesReturned);
        m_strIpparaCfg.read();

        String devices = "";
        if (!bRet){
            //设备不支持,则表示没有IP通道
            for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++){
                devices = "Camera" + (iChannum + m_strDeviceInfo.byStartChan);
            }
        }else{
            for(int iChannum =0; iChannum < HCNetSDK.MAX_IP_CHANNEL; iChannum++) {
                if (m_strIpparaCfg.struIPChanInfo[iChannum].byEnable == 1) {
                    devices = "IPCamera" + (iChannum + m_strDeviceInfo.byStartChan);
                }
            }
        }
        if(StringUtils.isNotEmpty(devices)){
            if(devices.charAt(0) == 'C'){//Camara开头表示模拟通道
                //子字符串中获取通道号
                iChannelNum = Integer.parseInt(devices.substring(6));
            }else{
                if(devices.charAt(0) == 'I'){//IPCamara开头表示IP通道
                    //子字符创中获取通道号,IP通道号要加32
                    iChannelNum = Integer.parseInt(devices.substring(8)) + 32;
                }else{
                    return 4;
                }
            }
        }
        return iChannelNum;
    }

    public void shutDownDev(){
        //如果已经注册,注销
        if (lUserID.longValue() > -1){
            hCNetSDK.NET_DVR_Logout_V30(lUserID);
        }
        hCNetSDK.NET_DVR_Cleanup();
    }

    /**
     * @Method rotateCamera
     * @Author zhengxin
     * @Description 摄像头转动
     * @Param: account 账号
     * @Param: password 密码
     * @Param: ip
     * @Param: port 端口
     * @Param: direction 转向
     * @Param: speed 转速
     * @Return boolean
     * @Date 2020/12/21 13:48
     * @Version  1.0
     */
    public boolean rotateCamera(String account,String password,String ip,String port,int direction,int speed){
        //初始化
        initDevices();
        //注册
        deviceRegist(account, password, ip, port);
        return  hCNetSDK.NET_DVR_PTZControlWithSpeed_Other(lUserID, new NativeLong(1l), direction, 0, speed);
    }

    /**
     * @Method shop
     * @Author zhengxin
     * @Description 摄像头停止转动
     * @Param: account 账号
     * @Param: password 密码
     * @Param: ip
     * @Param: port 端口
     * @Param: direction 转向
     * @Param: speed 转速
     * @Return boolean
     * @Date 2020/12/21 13:48
     * @Version  1.0
     */
    public boolean shop(String account,String password,String ip,String port,int direction,int speed){
        //初始化
        initDevices();
        //注册
        deviceRegist(account, password, ip, port);
        //转动
        return hCNetSDK.NET_DVR_PTZControlWithSpeed_Other(lUserID, new NativeLong(1l), direction, 1, speed);
    }


    public HttpResult playback(Map<String,String> map, String path){
        HttpResult httpResult=new HttpResult();
        if(StringUtils.isBlank(map.get("channel"))){
            logger.error("下载视频通道异常");
            return HttpResult.error("下载视频通道异常");
        }
        int channel=Integer.parseInt(map.get("channel"));
        //初始化
        initDevices();
        //注册
        deviceRegist(map.get("account"), map.get("password"), map.get("ip"), map.get("port"));
//        long lUserId = userId.longValue();
        long userID = lUserID.longValue();
        if(userID==-1) {
            logger.warn("hksdk(视频)-海康sdk登录失败!");
            return HttpResult.error("hksdk(视频)-海康sdk登录失败!");
        }
        loadHandle =new NativeLong(-1);
        if(loadHandle.intValue()==-1) {
            loadHandle =hCNetSDK.NET_DVR_PlayBackByTime(lUserID,new NativeLong(channel),
                    DateUtils.getNvrTime(map.get("startTime")),DateUtils.getNvrTime(map.get("endTime")),null);
            logger.info("hksdk(视频)-获取回放句柄信息,状态值:"+hCNetSDK.NET_DVR_GetLastError());
            if(loadHandle.intValue()>=0) {
//                    hCNetSDK.NET_DVR_CaptureJPEGPicture_NEW()
//                boolean  NET_DVR_CaptureJPEGPicture_NEW(NativeLong lUserID, NativeLong lChannel, NET_DVR_JPEGPARA lpJpegPara, String sJpegPicBuffer, int dwPicSize, IntByReference lpSizeReturned);
//                boolean  NET_DVR_SetPlayDataCallBack(NativeLong lPlayHandle, FPlayDataCallBack fPlayDataCallBack, int dwUser);
//
//                hCNetSDK.NET_DVR_SetPlayDataCallBack(loadHandle,);
                boolean downloadFlag = hCNetSDK.NET_DVR_PlayBackControl(loadHandle,hCNetSDK.NET_DVR_PLAYSTART,0,null);

            }
        }
        return null;
    }

    /**
     * @Method downloadVideo
     * @Author zhengxin
     * @Description 按时间下载视频
     * @Param: map 下载视频参数信息
     * @Return void
     * @Date 2021/4/28 15:30
     * @Version  1.0
     */
    public void downloadVideo(Map<String,String> map) {
        if(StringUtils.isBlank(map.get("channel"))){
            logger.error("下载视频通道异常");
        }
        //硬盘录像机通道数从33开始
        int channel=Integer.parseInt(map.get("channel"))+32;
        //初始化
        initDevices();
        //注册
        deviceRegist(map.get("account"), map.get("password"), map.get("ip"), map.get("dvrPort"));
        long userID = lUserID.longValue();
        if(userID==-1) {
            logger.warn("hksdk(视频)-海康sdk登录失败!");
        }
        loadHandle =new NativeLong(-1);
        if(loadHandle.intValue()==-1) {
            loadHandle = hCNetSDK.NET_DVR_GetFileByTime(lUserID,new NativeLong(channel),DateUtils.getNvrTime(map.get("startTime")),DateUtils.getNvrTime(map.get("endTime")),map.get("psPath"));
            logger.info("hksdk(视频)-获取播放句柄信息,状态值:"+hCNetSDK.NET_DVR_GetLastError());
            if(loadHandle.intValue()>=0) {
                boolean downloadFlag = hCNetSDK.NET_DVR_PlayBackControl(loadHandle,hCNetSDK.NET_DVR_PLAYSTART,0,null);
                int tmp = -1;
                IntByReference pos = new IntByReference();
                while(true) {
                    boolean backFlag = hCNetSDK.NET_DVR_PlayBackControl(loadHandle,hCNetSDK.NET_DVR_PLAYGETPOS,0,pos);
                    if(!backFlag) {//防止单个线程死循环
                        return;
                    }
                    int produce =pos.getValue();
                    if((produce%1)==0&&tmp!=produce) {//输出进度
                        tmp = produce;
                        //当下载进度为100时，将下载进度设置为99，将MPEG-PS转mpeg4
                        int progress=tmp;
                        if(tmp==100){
                            progress=tmp-1;
                        }
                        //保存下载进度
                        DOWNLOAD_MAP.put(map.get("mp4Path"),progress+"");
                        logger.info("hksdk(视频)-视频下载进度:"+"=="+produce+"%");
                    }
                    if(produce ==100) {//下载成功
                        hCNetSDK.NET_DVR_StopGetFile(loadHandle);
                        loadHandle.setValue(-1);
                        hCNetSDK.NET_DVR_Logout(lUserID);//退出录像机
                        logger.info("hksdk(视频)-退出状态"+hCNetSDK.NET_DVR_GetLastError());
                        try {
                            //将MPEG-PS转mpeg4
                            convetor(map.get("psPath"),map.get("mp4Path"));
                            Thread.currentThread().sleep(3000);
                            //将MPEG-PS转mpeg4完成进度为100
                            DOWNLOAD_MAP.put(map.get("mp4Path"),"100");
                            //删除MPEG-PS文件
                            FileUtils.delFile(map.get("psPath"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    if(produce>100) {//下载失败
                        hCNetSDK.NET_DVR_StopGetFile(loadHandle);
                        loadHandle.setValue(-1);
                        logger.warn("hksdk(视频)-海康sdk由于网络原因或DVR忙,下载异常终止!错误原因:"+ hCNetSDK.NET_DVR_GetLastError());
                        hCNetSDK.NET_DVR_Logout(lUserID);//退出录像机
                        break;
                    }
                }
            }else{
                logger.error("hksdk(视频)-下载失败" + hCNetSDK.NET_DVR_GetLastError());
            }
        }
    }

    /**
     * @Method playBackByTime
     * @Author zhengxin
     * @Description 根据时间回放
     * @Param: []
     * @Return void
     * @Date 2021/4/8 14:03
     * @Version  1.0
     */
    public void playBackByTime(Map<String,String> map){
        if(StringUtils.isBlank(map.get("channel"))){
            logger.error("下载视频通道异常");
//            return HttpResult.error("下载视频通道异常");
        }
        //初始化
        initDevices();
        //注册
        deviceRegist(map.get("account"), map.get("password"), map.get("ip"), map.get("dvrPort"));
        HCNetSDK.NET_DVR_VOD_PARA m_struPara = new HCNetSDK.NET_DVR_VOD_PARA();
        m_struPara.read();
        m_struPara.dwSize = m_struPara.size();

        HCNetSDK.NET_DVR_STREAM_INFO m_struStreamInfo = new HCNetSDK.NET_DVR_STREAM_INFO();
        m_struStreamInfo.read();
        m_struStreamInfo.dwSize = m_struStreamInfo.size();
        //回放通道从33开始
        m_struStreamInfo.dwChannel = Integer.parseInt(map.get("channel"))+32;
        m_struStreamInfo.write();
        m_struPara.struIDInfo = m_struStreamInfo;
//        //回放开始时间
//        HCNetSDK.NET_DVR_TIME startTime=DateUtils.getNvrTime(map.get("startTime"));
//        //回放停止时间
//        HCNetSDK.NET_DVR_TIME stopTime = DateUtils.getNvrTime(map.get("endTime"));
//        m_struPara.struBeginTime = startTime;
//        m_struPara.struEndTime = stopTime;
//        m_struPara.write();
        HCNetSDK.NET_DVR_TIME struStartTime = new HCNetSDK.NET_DVR_TIME();
        HCNetSDK.NET_DVR_TIME struStopTime = new HCNetSDK.NET_DVR_TIME();
        struStartTime.read();
        struStopTime.read();
        struStartTime.dwYear = 2021;//开始时间
        struStartTime.dwMonth = 4;
        struStartTime.dwDay = 26;
        struStartTime.dwHour = 8;
        struStartTime.dwMinute = 0;
        struStartTime.dwSecond = 0;
        struStopTime.dwYear = 2021;//结束时间
        struStopTime.dwMonth = 4;
        struStopTime.dwDay = 26;
        struStopTime.dwHour = 8;
        struStopTime.dwMinute = 20;
        struStopTime.dwSecond = 0;
        struStartTime.write();
        struStopTime.write();

        m_struPara.struBeginTime = struStartTime;
        m_struPara.struEndTime = struStopTime;

        m_struPara.write();

        //按流ID和时间回放录像文件。
        m_lPlayHandle = hCNetSDK.NET_DVR_PlayBackByTime_V40(lUserID.intValue(), m_struPara);
        if (m_lPlayHandle == -1) {
            logger.error("按时间回放失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
            return;
        }else{
            //还要调用该接口才能开始回放
            if(playCallback == null)
            {
                playCallback = new FPlayDataCallBack();
            }

            if(!hCNetSDK.NET_DVR_SetPlayDataCallBack_V40(m_lPlayHandle, playCallback, null)){
                logger.error("NET_DVR_SetPlayDataCallBack_V40 failed" +hCNetSDK.NET_DVR_GetLastError());
                return;
            }
            else{
                logger.info("NET_DVR_SetPlayDataCallBack_V40 succeed");
            }

            IntByReference intByRef = new IntByReference(0);
            Pointer lpInBuffer = intByRef.getPointer();

            hCNetSDK.NET_DVR_PlayBackControl_V40(m_lPlayHandle, HCNetSDK.NET_DVR_PLAYSTART, lpInBuffer, 4, null, null);
            logger.info("开始回放");
        }
    }
    File file = new File("E:\\mytest.mp4");
    class FPlayDataCallBack implements HCNetSDK.FPlayDataCallBack {
//        @Override
//        //预览回调
//        public void invoke(int lPlayHandle, int dwDataType, Pointer pBuffer, int dwBufSize, Pointer pUser)
//        {
//            try {
//                FileOutputStream m_file = new FileOutputStream(file, true);
//                long offset = 0;
//                ByteBuffer buffers = pBuffer.getByteBuffer(offset, dwBufSize);
//                byte [] bytes = new byte[dwBufSize];
//                buffers.rewind();
//                buffers.get(bytes);
//                m_file.write(bytes);
//                m_file.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }

        //预览回调
        @Override
        public void invoke(NativeLong lPlayHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize, int dwUser) {
            try {
                FileOutputStream m_file = new FileOutputStream(file, true);
                long offset = 0;
                ByteBuffer buffers = pBuffer.getPointer().getByteBuffer(offset, dwBufSize);
                byte [] bytes = new byte[dwBufSize];
                buffers.rewind();
                buffers.get(bytes);
                m_file.write(bytes);
                m_file.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
//            long offset = 0;
//            int err_index = 0;///推流过程中出现错误的次数
////            //获取回放流
//            ByteBuffer buffers =pBuffer.getPointer().getByteBuffer(offset,dwBufSize);
//            byte [] bytes = new byte[dwBufSize];
//            buffers.rewind();
//            buffers.get(bytes);
//            InputStream in = new ByteArrayInputStream(bytes);
//            try {
////                InputStream in = new FileInputStream(file);
//                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(in, 0);
//                grabber.setOption("stimeout", "2000000");
//                grabber.setVideoOption("vcodec", "copy");
//                grabber.setFormat("mpeg");
//                grabber.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
//                // h264编/解码器
//                grabber.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//                grabber.start();
//
//                FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtmp://127.0.0.1:1935/live/mp4test",
//                        grabber.getImageWidth(), grabber.getImageHeight(), 0);
//                recorder.setInterleaved(true);
//                // 设置比特率
//                recorder.setVideoBitrate(2500000);
//                // h264编/解码器
//                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//                // 封装flv格式
//                recorder.setFormat("flv");
//                recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
//                // 视频帧率(保证视频质量的情况下最低25，低于25会出现闪屏)
//                recorder.setFrameRate(grabber.getFrameRate());
//                // 关键帧间隔，一般与帧率相同或者是视频帧率的两倍
//                recorder.setGopSize((int) grabber.getFrameRate() * 2);
//                AVFormatContext fc = null;
//                fc = grabber.getFormatContext();
//                recorder.start(fc);
//                // 清空探测时留下的缓存
//                grabber.flush();
//
//                AVPacket pkt = null;
//                long dts = 0;
//                long pts = 0;
//
//                System.out.println("开始推流");
//                for (int no_frame_index = 0; no_frame_index < 5 || err_index < 5;) {
//                    pkt = grabber.grabPacket();
//                    if (pkt == null || pkt.size() <= 0 || pkt.data() == null) {
//                        // 空包记录次数跳过
//                        no_frame_index++;
//                        err_index++;
//                        continue;
//                    }
//                    // 获取到的pkt的dts，pts异常，将此包丢弃掉。
//                    if (pkt.dts() == avutil.AV_NOPTS_VALUE && pkt.pts() == avutil.AV_NOPTS_VALUE || pkt.pts() < dts) {
//                        err_index++;
//                        avcodec.av_packet_unref(pkt);
//                        continue;
//                    }
//                    // 记录上一pkt的dts，pts
//                    dts = pkt.dts();
//                    pts = pkt.pts();
//                    // 推数据包
//                    err_index += (recorder.recordPacket(pkt) ? 0 : 1);
//                    // 将缓存空间的引用计数-1，并将Packet中的其他字段设为初始值。如果引用计数为0，自动的释放缓存空间。
//                    avcodec.av_packet_unref(pkt);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                System.out.println("推流结束");
//            }
        }
    }


//    public static void main(String[] args) {
//
//        HCNetUtils test = new HCNetUtils();
//        Map<String,String> map=new HashMap<>();
//        map.put("ip","218.106.157.166");
//        map.put("dvrPort","6002");
//        map.put("password","ydyf2020");
//        map.put("account","admin");
//        map.put("startTime","2021-04-26 8:00:00");
//        map.put("endTime","2021-04-26 8:10:00");
//        map.put("channel","1");
//        map.put("code","331");
//        map.put("filePath",PATH);
////        Dvr dvr = new Dvr(0,"录像机ip",8000,"录像机用户名","录像机密码",null,0);
////        int channel =33;//通道
//        test.downloadVideo(map);
//        while(true);
//    }

    private final static String PATH = "E://mytest.mp4";



//    public static void main(String[] args) {
//        try {
//            convetor("E:\\mytest.mp4","E:\\test.mp4");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * @Method convetor
     * @Author zhengxin
     * @Description 将MPEG-PS转MP4
     * @Param: videoInputPath MPEG-PS文件位子
     * @Param: videoOutPath MP4 输出位子
     * @Return void
     * @Date 2021/4/28 15:26
     * @Version  1.0
     */
    //ffmpeg -i demo.mp4 -c copy -an demp_enc.mp4
    public static void convetor(String videoInputPath, String videoOutPath) throws Exception {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(videoInputPath);
        command.add("-c");
        command.add("copy");
        command.add("-an");
        command.add(videoOutPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }

    public static void main(String[] args) {
        HCNetUtils test = new HCNetUtils();
        Map<String,String> map=new HashMap<>();
        map.put("ip","218.106.157.166");
        map.put("dvrPort","8000");
        map.put("password","ydyf2020");
        map.put("account","admin");
        map.put("channel","1");
        map.put("imgPath","G:\\test\\test.jpg");

        test.getDVRPic(map);
    }

    /**
     * 抓拍图片
     * @param
     */
    public void getDVRPic(Map<String,String> map) {
        if(StringUtils.isBlank(map.get("channel"))){
            logger.error("下载视频通道异常");
        }
        NativeLong chanLong = new NativeLong(Integer.parseInt(map.get("channel")));

        //初始化
        initDevices();
        HCNetSDK.NET_DVR_DEVICEINFO_V30 devinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();// 设备信息
        //注册设备
        deviceRegist(map.get("account"), map.get("password"), map.get("ip"), map.get("dvrPort"));
        long userID = lUserID.longValue();
        if(userID==-1) {
            logger.warn("hksdk(视频)-海康sdk登录失败!");
        }
        HCNetSDK.NET_DVR_WORKSTATE_V30 devwork = new HCNetSDK.NET_DVR_WORKSTATE_V30();
        if (!hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, devwork)) {
            // 返回Boolean值，判断是否获取设备能力
            logger.info("hksdk(抓图)-返回设备状态失败");
        }
        //图片质量
        HCNetSDK.NET_DVR_JPEGPARA jpeg = new HCNetSDK.NET_DVR_JPEGPARA();
        //设置图片分辨率
        jpeg.wPicSize = 5;
        //设置图片质量
        jpeg.wPicQuality = 0;
        IntByReference a = new IntByReference();
        //设置图片大小
        ByteBuffer jpegBuffer = ByteBuffer.allocate(1024 * 1024);
        //String jpegBuffer ="1024 * 1024";
        File file = new File(map.get("imgPath"));
        // 抓图到内存，单帧数据捕获并保存成JPEG存放在指定的内存空间中
        //需要加入通道
        boolean is = hCNetSDK.NET_DVR_CaptureJPEGPicture_NEW(lUserID, chanLong, jpeg, jpegBuffer, 1024 * 1024, a);
        if (is) {
            logger.info("hksdk(抓图)-结果状态值(0表示成功):" + hCNetSDK.NET_DVR_GetLastError());
            //存储到本地
            BufferedOutputStream outputStream = null;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(jpegBuffer.array(), 0, a.getValue());
                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            logger.info("hksdk(抓图)-抓取失败,错误码:" + hCNetSDK.NET_DVR_GetLastError());
        }

        hCNetSDK.NET_DVR_Logout(lUserID);//退出登录
        //hcNetSDK.NET_DVR_Cleanup();

    }
}
