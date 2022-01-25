package com.substation.controller.camera;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.substation.camera.cache.PushStreamCache;
import com.substation.camera.config.StreamConfig;
import com.substation.camera.thread.CameraThread;
import com.substation.entity.Camera;
import com.substation.entity.Device;
import com.substation.entity.system.SysUser;
import com.substation.enums.InitEnum;
import com.substation.http.HttpResult;
import com.substation.service.IDeviceService;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysConfigService;
import com.substation.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * @ClassName: CameraController
 * @Author: zhengxin
 * @Description: 视频流处理接口
 * @Date: 2021/3/24 14:07
 * @Version: 1.0
 */
@Api(value = "视频流处理接口",tags = {"视频流：视频流处理接口"})
@RestController
@RequestMapping("/camera")
public class CameraController {

    private final static Logger logger = LoggerFactory.getLogger(CameraController.class);

    // 存放任务 线程
    public static Map<String, CameraThread.CameraRunnable> JOBMAP = new HashMap<String, CameraThread.CameraRunnable>();

    @Autowired
    public StreamConfig config;// 配置文件bean

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private ISysConfigService configService;

    /**
     * @Method openCamera
     * @Author zhengxin
     * @Description 开启视频流
     * @Param: []
     * @Return com.yddl.http.HttpResult
     * @Date 2021/3/24 16:57
     * @Version  1.0
     */
    @ApiOperation(value = "开启视频流",httpMethod ="GET")
    @GetMapping("/openCamera")
    public HttpResult openCamera(){
        HttpResult result=new HttpResult();
        //获取映射到外网的ip
        String publicIp = configService.selectConfigByKey(InitEnum.PUBLIC_IP.key);
        if(StringUtils.isBlank(publicIp))
            publicIp= IpUtils.getHostIp();
        //获取流服务器端口映射到外网的端口号
        String streamServerPort = configService.selectConfigByKey(InitEnum.STREAM_SERVER_PORT.key);
        if (StringUtils.isBlank(streamServerPort))
            streamServerPort=InitEnum.STREAM_SERVER_PORT.defaultkey;
        // openStream返回结果
        Map<String, Object> openMap = new HashMap<>();
        // 返回结果
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        //根据用户所属单位和摄像机所属单位获取数据
        SysUser user = loginUserService.getLoginUser();
        List<Device> devices = deviceService.queryDeviceListByDept(user);
        // 需要校验非空的参数
        String[] isNullArr = { "ip", "account", "password", "channel" };
        for (Device device : devices) {
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            //设备编码（序列号）
            map.put("code",device.getCode());
            JSONObject json = JSON.parseObject(JSONObject.toJSONString(device));
            // 空值校验
            if(!ValidationEmptyUtil.isNullParameters(json,isNullArr)){
                return HttpResult.error(1,"输入参数不完整");
            }

            // ip格式校验
            if(!IpUtils.internalIp(device.getIp())){
                return HttpResult.error(2,"ip格式输入错误");
            }

            Camera camera=new Camera();
            // 获取当前时间
            String opentime = DateUtils.getTime();
            Set<String> keys = PushStreamCache.STREATMAP.keySet();
            // 缓存是否为空
            if (0 == keys.size()) {
                // 开始推流
                openMap = openStream(device.getIp(), device.getAccount(), device.getPassword(), device.getChannel(),
                        "main", null, null, DateUtils.getTime());
                if (Integer.parseInt(openMap.get("errorcode").toString()) == 0) {
//                    map.put("url", ((Camera) openMap.get("camera")).getUrl());
                    map.put("url", "http://"+publicIp+":"+streamServerPort+"/live?port=1935&app=live&stream="+ ((Camera)openMap.get("camera")).getToken());
                    map.put("token", ((Camera)openMap.get("camera")).getToken());
                    list.add(map);
                    result.setCode(0);
                    result.setMsg("打开视频流成功");
                } else {
                    result.setMsg(openMap.get("message").toString());
                    result.setCode((int)openMap.get("errorcode"));
                }
            }else{
                // 是否存在的标志；false：不存在；true：存在
                boolean sign = false;
//                if (null == camera.getStarttime()) {// 直播流
                    for (String key : keys) {
                        if (device.getIp().equals(PushStreamCache.STREATMAP.get(key).getIp())
                                && device.getChannel().equals(PushStreamCache.STREATMAP.get(key).getChannel())
                                && null == PushStreamCache.STREATMAP.get(key).getStarttime()) {// 存在直播流
                            camera = PushStreamCache.STREATMAP.get(key);
                            sign = true;
                            break;
                        }
                    }
                    if (sign) {// 存在
                        camera.setCount(camera.getCount() + 1);
                        camera.setOpentime(opentime);
                        map.put("url", "http://"+publicIp+":"+streamServerPort+"/live?port=1935&app=live&stream="+ camera.getToken());
//                        map.put("url", camera.getUrl());
                        map.put("token", camera.getToken());
                        list.add(map);
                        result.setCode(0);
                        result.setMsg("打开视频流成功");
                    } else {
                        openMap = openStream(device.getIp(), device.getAccount(), device.getPassword(), device.getChannel(),
                                "main", null, null, DateUtils.getTime());
                        if (Integer.parseInt(openMap.get("errorcode").toString()) == 0) {
//                            map.put("url", ((Camera) openMap.get("camera")).getUrl());
                            map.put("url", "http://"+publicIp+":"+streamServerPort+"/live?port=1935&app=live&stream="+ ((Camera)openMap.get("camera")).getToken());
                            map.put("token", ((Camera) openMap.get("camera")).getToken());
                            list.add(map);
                            result.setCode(0);
                            result.setMsg("打开视频流成功");
                        } else {
                            result.setMsg(openMap.get("message").toString());
                            result.setCode((int)openMap.get("errorcode"));
                        }
                    }
            }
        }
        result.setData(list);
        return result;
    }

    /**
     * @Method openCameraByCode
     * @Author zhengxin
     * @Description 根据摄像头编号开启视频流
     * @Param: [code 摄像头编码]
     * @Return com.yddl.http.HttpResult
     * @Date 2021/3/29 15:56
     * @Version  1.0
     */
    @ApiOperation(value = "根据摄像头编号开启视频流",httpMethod ="GET")
    @GetMapping("/openCameraByCode/{code}")
    public HttpResult openCameraByCode(
            @ApiParam(name = "code",value = "摄像头编码",required = true)
            @PathVariable String code){

        HttpResult result=new HttpResult();
        //获取映射到外网的ip
        String publicIp = configService.selectConfigByKey(InitEnum.PUBLIC_IP.key);
        if(StringUtils.isBlank(publicIp))
            publicIp=IpUtils.getHostIp();
        //获取流服务器端口映射到外网的端口号
        String streamServerPort = configService.selectConfigByKey(InitEnum.STREAM_SERVER_PORT.key);
        if (StringUtils.isBlank(streamServerPort))
            streamServerPort=InitEnum.STREAM_SERVER_PORT.defaultkey;
        // openStream返回结果
        Map<String, Object> openMap = new HashMap<>();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        //根据摄像头编码获取设备信息
        Device device = deviceService.queryDeviceByCode(code);
        //设备编码（序列号）
        map.put("code",device.getCode());
        // 需要校验非空的参数
        String[] isNullArr = { "ip", "account", "password", "channel" };
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(device));
        // 空值校验
        if(!ValidationEmptyUtil.isNullParameters(json,isNullArr)){
            return HttpResult.error(1,"输入参数不完整");
        }

        // ip格式校验
        if(!IpUtils.internalIp(device.getIp())){
            return HttpResult.error(2,"ip格式输入错误");
        }
        Camera camera=new Camera();
        // 获取当前时间
        String opentime = DateUtils.getTime();
        Set<String> keys = PushStreamCache.STREATMAP.keySet();
        // 缓存是否为空
        if (0 == keys.size()) {
            // 开始推流
            openMap = openStream(device.getIp(), device.getAccount(), device.getPassword(), device.getChannel(),
                    "main", null, null, DateUtils.getTime());
            if (Integer.parseInt(openMap.get("errorcode").toString()) == 0) {
                map.put("url", "http://"+publicIp+":"+streamServerPort+"/live?port=1935&app=live&stream="+ ((Camera)openMap.get("camera")).getToken());
                map.put("token", ((Camera)openMap.get("camera")).getToken());
                result.setCode(0);
                result.setMsg("打开视频流成功");
            } else {
                result.setMsg(openMap.get("message").toString());
                result.setCode((int)openMap.get("errorcode"));
            }
        }else{
            // 是否存在的标志；false：不存在；true：存在
            boolean sign = false;
            for (String key : keys) {
                if (device.getIp().equals(PushStreamCache.STREATMAP.get(key).getIp())
                        && device.getChannel().equals(PushStreamCache.STREATMAP.get(key).getChannel())
                        && null == PushStreamCache.STREATMAP.get(key).getStarttime()) {// 存在直播流
                    camera = PushStreamCache.STREATMAP.get(key);
                    sign = true;
                    break;
                }
            }
            if (sign) {// 存在
                camera.setCount(camera.getCount() + 1);
                camera.setOpentime(opentime);
                map.put("url", "http://"+publicIp+":"+streamServerPort+"/live?port=1935&app=live&stream="+ camera.getToken());
                map.put("token", camera.getToken());
                result.setCode(0);
                result.setMsg("打开视频流成功");
            } else {
                openMap = openStream(device.getIp(), device.getAccount(), device.getPassword(), device.getChannel(),
                        "main", null, null, DateUtils.getTime());
                if (Integer.parseInt(openMap.get("errorcode").toString()) == 0) {
                    map.put("url", "http://"+publicIp+":"+streamServerPort+"/live?port=1935&app=live&stream="+ ((Camera)openMap.get("camera")).getToken());
                    map.put("token", ((Camera) openMap.get("camera")).getToken());
                    result.setCode(0);
                    result.setMsg("打开视频流成功");
                } else {
                    result.setMsg(openMap.get("message").toString());
                    result.setCode((int)openMap.get("errorcode"));
                }
            }
        }
        result.setData(map);
        return result;
    }

    /**
     * @Method openStream
     * @Author zhengxin
     * @Description 推流器
     * @param  ip ip
     * @param username 账号
     * @param password 密码
     * @param channel 通道
     * @param stream 流类型
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @param opentime 打开视频流时间
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Date 2021/3/24 16:25
     * @Version  1.0
     */
    private Map<String, Object> openStream(String ip, String username, String password, String channel, String stream,
                                           String starttime, String endtime, String opentime) {
        Map<String, Object> map = new HashMap<>();
        Camera camera = new Camera();
        // 生成token
        String token = UUID.randomUUID().toString();
        String rtsp = "";
        String rtmp = "";
        String IP = IpUtils.IpConvert(ip);
        String url = "";
        boolean sign = false;// 该nvr是否再回放，true：在回放；false： 没在回放
        // 历史流
        if (null != starttime && !"".equals(starttime)) {
            if (null != endtime && !"".equals(endtime)) {
                rtsp = "rtsp://" + username + ":" + password + "@" + IP + ":6002/Streaming/tracks/"
                        + (Integer.valueOf(channel)) + "01?starttime=" + DateUtils.getTime(starttime).substring(0, 8)
                        + "T" + DateUtils.getTime(starttime).substring(8) + "Z&endtime="
                        + DateUtils.getTime(endtime).substring(0, 8) + "T" + DateUtils.getTime(endtime).substring(8) + "Z";
                camera.setStarttime(DateUtils.getTime(starttime));
                camera.setEndTime(DateUtils.getTime(endtime));
            } else {
                String startTime = DateUtils.getStarttime(starttime);
                String endTime = DateUtils.getEndtime(starttime);
                rtsp = "rtsp://" + username + ":" + password + "@" + IP + ":6002/Streaming/tracks/"
                        + (Integer.valueOf(channel)) + "01?starttime=" + startTime.substring(0, 8) + "t"
                        + startTime.substring(8) + "z'&'endtime=" + endTime.substring(0, 8) + "t" + endTime.substring(8)
                        + "z";
                camera.setStarttime(DateUtils.getStarttime(starttime));
                camera.setEndTime(DateUtils.getEndtime(starttime));
            }
//			rtmp = "rtmp://" + Utils.IpConvert(config.getPush_host()) + ":" + config.getPush_port() + "/history/"
//					+ token;
            rtmp = "rtmp://" + IpUtils.IpConvert(config.getPush_host()) + ":" + config.getPush_port() + "/history/test";
            if (config.getHost_extra().equals("127.0.0.1")) {
                url = rtmp;
            } else {
                url = "rtmp://" + IpUtils.IpConvert(config.getHost_extra()) + ":" + config.getPush_port() + "/history/"
                        + token;
            }
        } else {// 直播流
            rtsp = "rtsp://" + username + ":" + password + "@" + IP + ":554/h264/ch" + channel + "/" + stream
                    + "/av_stream";
            rtmp = "rtmp://" + IpUtils.IpConvert(config.getPush_host()) + ":" + config.getPush_port() + "/live/" + token;
            if (config.getHost_extra().equals("127.0.0.1")) {
                url = rtmp;
            } else {
                url = "rtmp://" + IpUtils.IpConvert(config.getHost_extra()) + ":" + config.getPush_port() + "/live/"
                        + token;
            }
        }

        camera.setUsername(username);
        camera.setPassword(password);
        camera.setIp(IP);
        camera.setChannel(channel);
        camera.setStream(stream);
        camera.setRtsp(rtsp);
        camera.setRtmp(rtmp);
        camera.setUrl(url);
        camera.setOpentime(opentime);
        camera.setCount(1);
        camera.setToken(token);

        // 解决ip输入错误时，grabber.start();出现阻塞无法释放grabber而导致后续推流无法进行；
        Socket rtspSocket = new Socket();
        Socket rtmpSocket = new Socket();

        // 建立TCP Scoket连接，超时时间1s，如果成功继续执行，否则return
        try {
            rtspSocket.connect(new InetSocketAddress(camera.getIp(), 554), 1000);
        } catch (IOException e) {
            logger.error("与拉流IP：   " + camera.getIp() + "   端口：   554    建立TCP连接失败！");
            map.put("camera", camera);
            map.put("errorcode", 6);
            map.put("message", "与拉流IP：   " + camera.getIp() + "   端口：   554    建立TCP连接失败！");
            return map;
        }
        try {
            rtmpSocket.connect(new InetSocketAddress(IpUtils.IpConvert(config.getPush_host()),
                    Integer.parseInt(config.getPush_port())), 1000);
        } catch (IOException e) {
            logger.error("与推流IP：   " + config.getPush_host() + "   端口：   " + config.getPush_port() + " 建立TCP连接失败！");
            map.put("camera", camera);
            map.put("errorcode", 7);
            map.put("message",
                    "与推流IP:" + config.getPush_host() + " 端口: " + config.getPush_port() + " 建立连接失败,请检查nginx服务");
            return map;
        }
        // 执行任务
        CameraThread.CameraRunnable job = new CameraThread.CameraRunnable(camera);
        CameraThread.CameraRunnable.es.execute(job);
        JOBMAP.put(token, job);
        map.put("camera", camera);
        map.put("errorcode", 0);
        map.put("message", "打开视频流成功");
        return map;
    }

    @ApiOperation(value = "关闭视频流",httpMethod ="DELETE")
    @DeleteMapping("/closeCamera/{tokens}")
    public void closeCamera(
            @ApiParam(name = "tokens",value = "视频流token",required = true)
            @PathVariable("tokens") String tokens) {
        if(StringUtils.isNotBlank(tokens)){
            String[] tokenArr = tokens.split(",");
            for (String token : tokenArr) {
                if (JOBMAP.containsKey(token) && PushStreamCache.STREATMAP.containsKey(token)) {
                    // 回放手动关闭
                    if (null != PushStreamCache.STREATMAP.get(token).getStarttime()) {
                        if (0 == PushStreamCache.STREATMAP.get(token).getCount() - 1) {
                            PushStreamCache.PUSHMAP.get(token).setExitcode(1);
                            PushStreamCache.STREATMAP.get(token).setCount(PushStreamCache.STREATMAP.get(token).getCount() - 1);
                        } else {
                            PushStreamCache.STREATMAP.get(token).setCount(PushStreamCache.STREATMAP.get(token).getCount() - 1);
                            logger.info("当前设备正在进行回放，使用人数为" + PushStreamCache.STREATMAP.get(token).getCount() + " 设备信息：[ip："
                                    + PushStreamCache.STREATMAP.get(token).getIp() + " channel:"
                                    + PushStreamCache.STREATMAP.get(token).getChannel() + " stream:"
                                    + PushStreamCache.STREATMAP.get(token).getStream() + " statrtime:"
                                    + PushStreamCache.STREATMAP.get(token).getStream() + " endtime:"
                                    + PushStreamCache.STREATMAP.get(token).getEndtime() + " url:"
                                    + PushStreamCache.STREATMAP.get(token).getUrl() + "]");
                        }
                    } else {
                        if (0 < PushStreamCache.STREATMAP.get(token).getCount()) {
                            // 人数-1
                            PushStreamCache.STREATMAP.get(token).setCount(PushStreamCache.STREATMAP.get(token).getCount() - 1);
                            logger.info("关闭成功 当前设备使用人数为" + PushStreamCache.STREATMAP.get(token).getCount() + " 设备信息：[ip："
                                    + PushStreamCache.STREATMAP.get(token).getIp() + " channel:"
                                    + PushStreamCache.STREATMAP.get(token).getChannel() + " stream:"
                                    + PushStreamCache.STREATMAP.get(token).getStream() + " statrtime:"
                                    + PushStreamCache.STREATMAP.get(token).getStream() + " endtime:"
                                    + PushStreamCache.STREATMAP.get(token).getEndtime() + " url:"
                                    + PushStreamCache.STREATMAP.get(token).getUrl() + "]");
                        }
                    }

                }
            }
        }
    }

    /**
     * @Method keepAlive
     * @Author zhengxin
     * @Description 视频流保活
     * @Param: [tokens 视频流tokens]
     * @Return void
     * @Date 2021/3/29 15:34
     * @Version  1.0
     */
    @ApiOperation(value = "视频流保活",httpMethod ="PUT")
    @PutMapping("/keepAlive/{tokens}")
    public void keepAlive(
            @ApiParam(name = "tokens",value = "视频流tokens",required = true)
            @PathVariable("tokens") String tokens) {
        // 校验参数
        if (null != tokens && !"".equals(tokens)) {
            String[] tokenArr = tokens.split(",");
            for (String token : tokenArr) {
                Camera camera = new Camera();
                // 直播流token
                if (null != PushStreamCache.STREATMAP.get(token)) {
                    camera = PushStreamCache.STREATMAP.get(token);
                    // 更新当前系统时间
                    camera.setOpentime(DateUtils.getTime());
                    logger.info("保活成功 设备信息：[ip：" + camera.getIp() + " channel:" + camera.getChannel()
                            + " stream:" + camera.getStream() + " starttime:" + camera.getStarttime()
                            + " endtime:" + camera.getEndtime() + " url:" + camera.getUrl() + "]");
                }
            }
        }
    }

    /**
     * @Method cameraRotation
     * @Author zhengxin
     * @Description 摄像头转动
     * @Param: code 设备编码（序列号）, direction 转动方向
     * @Return com.yddl.http.HttpResult
     * @Date 2020/12/19 14:00
     * @Version  1.0
     */
    @ApiOperation(value = "摄像头转动",httpMethod ="GET")
    @GetMapping(value = "/cameraRotation/{code}/{direction}")
    public HttpResult cameraRotation(
            @ApiParam(name = "code",value = "设备编码（序列号）")
            @PathVariable String code,
            @ApiParam(name = "direction",value = "转动方向")
            @PathVariable String direction) throws Exception {
        if(StringUtils.isBlank(code))
            return HttpResult.error("设备编码（序列号）不能为空");
        if(StringUtils.isBlank(direction))
            return HttpResult.error("转动方向不正确");
        Device device = deviceService.queryDeviceByCode(code);
        Map<String,String> map= ObjectToMapUtil.convert(device);
        boolean res= CameraOperationUtil.rotateCamera(map,direction);
        if(!res)
            HttpResult.error("设备暂不支持转动");
        return  HttpResult.ok();
    }

    /**
     * @Method shopRotation
     * @Author zhengxin
     * @Description 停止摄像头转动
     * @Param: [code 设备编码（序列号, direction 转动方向]
     * @Return com.yddl.http.HttpResult
     * @Date 2020/12/21 11:59
     * @Version  1.0
     */
    @ApiOperation(value = "停止摄像头转动",httpMethod ="GET")
    @GetMapping(value = "/shopRotation/{code}/{direction}")
    public HttpResult shopRotation(
            @ApiParam(name = "code",value = "设备编码（序列号）")
            @PathVariable String code,
            @ApiParam(name = "direction",value = "转动方向")
            @PathVariable String direction) throws Exception {

        if(StringUtils.isBlank(code))
            return HttpResult.error("设备编码（序列号）不能为空");
        if(StringUtils.isBlank(direction))
            return HttpResult.error("转动方向不正确");
        Device device = deviceService.queryDeviceByCode(code);
        Map<String,String> map= ObjectToMapUtil.convert(device);
        boolean res= CameraOperationUtil.shop(map,direction);
        if(!res)
            HttpResult.error("设备停止转动失败");
        return HttpResult.ok();
    }


    /**
     * @Method downloadHistoryVideo
     * @Author zhengxin
     * @Description 下载历史视频
     * @Param: code 设备编码（序列号）
     * @Param: startTime 开始时间
     * @Param: endTime 结束时间
     * @Return com.yddl.http.HttpResult
     * @Date 2021/4/28 10:18
     * @Version  1.0
     */
    @ApiOperation(value = "下载历史视频",httpMethod ="GET")
    @GetMapping(value = "/downloadHistoryVideo/{code}/{startTime}/{endTime}")
    public void downloadHistoryVideo(
            @ApiParam(name = "code",value = "设备编码（序列号）")
            @PathVariable String code,
            @ApiParam(name = "startTime",value = "开始时间")
            @PathVariable String startTime,
            @ApiParam(name = "endTime",value = "结束时间")
            @PathVariable String endTime){
        System.gc();
        //清除多余历史视频
        for (String key : HCNetUtils.DOWNLOAD_MAP.keySet()) {
            FileUtils.delFile(key);
        }
        //下载MPEG-PS路径
        String psPath=code+DateUtils.dateTimeNow();
        //转换后mp4路径
        String mp4Path=code+DateUtils.stringToTime(startTime)+DateUtils.stringToTime(endTime);
        //比对缓存中是否存在一样的文件
        String progress = HCNetUtils.DOWNLOAD_MAP.get(FileUtils.videoFilePath(mp4Path));
        if("100".equals(progress)) {
            return;
        }
        if(StringUtils.isBlank(code)) {
            return;
        }
        FileUtils.deleteDirectory(FileUtils.getCataloguePath(ConstantUtil.HISTORY_VIDEO_FILE_PATH));
        //根据设备编号获取设备信息
        Device device = deviceService.queryDeviceByCode(code);
        try {
            Map<String,String> map=ObjectToMapUtil.convert(device);
            map.put("psPath",FileUtils.videoFilePath(psPath));
            map.put("mp4Path",FileUtils.videoFilePath(mp4Path));
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            //下载视频
            CameraOperationUtil.downloadVideo(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Method downloadProgress
     * @Author zhengxin
     * @Description 获取下载进度
     * @Param: code 设备编码（序列号）
     * @Param: startTime 开始时间
     * @Param: endTime 结束时间
     * @Return com.yddl.http.HttpResult
     * @Date 2021/4/27 14:29
     * @Version  1.0
     */
    @ApiOperation(value = "获取下载进度",httpMethod ="GET")
    @GetMapping(value = "/downloadProgress/{code}/{startTime}/{endTime}")
    public HttpResult downloadProgress(
            @ApiParam(name = "code",value = "设备编码（序列号）")
            @PathVariable String code,
            @ApiParam(name = "startTime",value = "开始时间")
            @PathVariable String startTime,
            @ApiParam(name = "endTime",value = "结束时间")
            @PathVariable String endTime){
        HttpResult httpResult=new HttpResult();
        //获取映射到外网的ip
        String publicIp = configService.selectConfigByKey(InitEnum.PUBLIC_IP.key);
        if(StringUtils.isBlank(publicIp))
            publicIp=IpUtils.getHostIp();
        //获取映射到外网的web端口号
        String webPort = configService.selectConfigByKey(InitEnum.WEB_PORT.key);
        if (StringUtils.isBlank(webPort))
            webPort=InitEnum.WEB_PORT.defaultkey;
        Map<String,String> map=new HashMap<String,String>();
        //转换后mp4路径
        String mp4Path=code+DateUtils.stringToTime(startTime)+DateUtils.stringToTime(endTime);
        String progress = HCNetUtils.DOWNLOAD_MAP.get(FileUtils.videoFilePath(mp4Path));
        map.put("progress",progress);
        if(StringUtils.isBlank(progress))
            return httpResult.error("暂无该时段的历史视频信息！");
        if("100".equals(progress)){
            map.put("url","http://"+publicIp+":"+webPort+"/camera/showVideo?fileName="+mp4Path);
        }
        httpResult.setData(map);
        return httpResult;
    }

    /**
     * @Method showVideo
     * @Author zhengxin
     * @Description  根据本地视频全路径，响应给浏览器1个视频
     * @param response
     * @param fileName  文件全路径
     * @Return void
     * @Date 2021/4/28 15:36
     * @Version  1.0
     */
    @ApiOperation(value = "根据本地视频全路径，响应给浏览器1个视频",httpMethod ="GET")
    @RequestMapping("/showVideo")
    public void showVideo(HttpServletResponse response,
                          @RequestParam("fileName")String fileName) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String filePath=FileUtils.videoFilePath(fileName);
        show(response,filePath,"video");
    }

    /**
     * @Method show
     * @Author zhengxin
     * @Description 响应文件
     * @param response
     * @param fileName  文件全路径
     * @param type  响应流类型
     * @Return void
     * @Date 2021/4/28 15:36
     * @Version  1.0
     */
    public void  show(HttpServletResponse response, String fileName,String type){
        FileInputStream fis =null;
        OutputStream toClient=null;
        try{
            fis = new FileInputStream(fileName); // 以byte流的方式打开文件
            int i=fis.available(); //得到文件大小
            byte data[]=new byte[i];
            fis.read(data);  //读数据
            //要vue-video-player拖动播放需要添加下面两个header
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", String.valueOf(i));
            response.setContentType(type+"/*"); //设置返回的文件类型
            toClient=response.getOutputStream(); //得到向客户端输出二进制数据的对象
            toClient.write(data);  //输出数据
            toClient.flush();
        }catch(Exception e){
        }finally {
            try {
                if(toClient!=null){
                    toClient.close();
                }
                if(fis!=null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
