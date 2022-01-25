package com.substation.service;

import com.substation.entity.Device;
import com.substation.entity.dto.DeviceDto;
import com.substation.entity.system.SysUser;

import java.util.List;

/**
 * @ClassName: IDeviceService
 * @Author: zhengxin
 * @Description: 视频设备 业务层
 * @Date: 2020/12/15 16:21
 * @Version: 1.0
 */
public interface IDeviceService {

    /**
     * @Method queryDeviceListByDept
     * @Author zhengxin
     * @Description 根据部门查询视频设备列表
     * @Param: [user] 登录用户
     * @Return java.util.List<com.yddl.entity.Device>
     * @Date 2020/12/15 16:24
     * @Version  1.0
     */
    public List<Device> queryDeviceListByDept(SysUser user);

    /**
     * @Method selectDeviceList
     * @Author zhengxin
     * @Description 获取视频设备列表
     * @Param: [deviceDto] 视频设备信息
     * @Return java.util.List<com.yddl.entity.Device>
     * @Date 2020/12/16 11:49
     * @Version  1.0
     */
    public List<Device> selectDeviceList(DeviceDto deviceDto);

    /**
     * @Method selectCameraToNvr
     * @Author zhengxin
     * @Description 获取已加入硬盘录像机的视频设备列表
     * @Return java.util.List<com.yddl.entity.Device>
     * @Date 2021/4/29 13:59
     * @Version  1.0
     */
    public List<Device> selectCameraToNvrList();

    /**
     * @Method selectDeviceById
     * @Author zhengxin
     * @Description 根据视频设备id获取详情
     * @Param: [deviceId] 视频设备id
     * @Return com.yddl.entity.Device
     * @Date 2020/12/16 14:23
     * @Version  1.0
     */
    public Device selectDeviceById(Long deviceId);

    /**
     * @Method checkDeviceCodeUnique
     * @Author zhengxin
     * @Description 设备编码是否唯一
     * @Param: [code] 设备编码
     * @Return boolean
     * @Date 2020/12/16 14:36
     * @Version  1.0
     */
    public boolean checkDeviceCodeUnique(String code);

    /**
     * @Method queryDeviceByCode
     * @Author zhengxin
     * @Description 根据设备编码获取视频设备信息
     * @Param: [code] 设备编码
     * @Return com.yddl.entity.Device
     * @Date 2020/12/21 11:43
     * @Version  1.0
     */
    public Device queryDeviceByCode(String code);

    /**
     * @Method insertDevice
     * @Author zhengxin
     * @Description 新增视频设备
     * @Param: [deviceDto] 视频设备信息
     * @Return int
     * @Date 2020/12/16 14:47
     * @Version  1.0
     */
    public int insertDevice(DeviceDto deviceDto);

    /**
     * @Method updateDevice
     * @Author zhengxin
     * @Description 修改视频设备
     * @Param: [deviceDto] 视频设备信息
     * @Return int
     * @Date 2020/12/16 15:16
     * @Version  1.0
     */
    public int updateDevice(DeviceDto deviceDto);

    /**
     * @Method deleteDeviceByIds
     * @Author zhengxin
     * @Description 批量删除视频设备
     * @Param: [deviceIds] 视频设备ID数组
     * @Return int
     * @Date 2020/12/16 15:22
     * @Version  1.0
     */
    public int deleteDeviceByIds(Long[] deviceIds);

}
