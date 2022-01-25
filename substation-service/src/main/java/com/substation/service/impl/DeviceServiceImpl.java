package com.substation.service.impl;

import com.substation.entity.Device;
import com.substation.entity.dto.DeviceDto;
import com.substation.entity.system.SysUser;
import com.substation.enums.DeviceTypeEnum;
import com.substation.mapper.DeviceMapper;
import com.substation.service.IDeviceService;
import com.substation.service.LoginUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: DeviceServiceImpl
 * @Author: zhengxin
 * @Description: 视频设备 业务层
 * @Date: 2020/12/15 16:22
 * @Version: 1.0
 */
@Service
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private LoginUserService loginUserService;


    /**
     * @Method queryDeviceListByDept
     * @Author zhengxin
     * @Description 根据部门查询视频设备列表
     * @Param: [user] 登录用户
     * @Return java.util.List<com.yddl.entity.Device>
     * @Date 2020/12/15 16:24
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Device> queryDeviceListByDept(SysUser user) {
        List<Device> devices =null;
        //是否是管理员
        if(user.isAdmin()){
            devices = deviceMapper.selectAll();
        }else{
            DeviceDto deviceDto=new DeviceDto();
//            Example example=new Example(Device.class);
//            Example.Criteria criteria = example.createCriteria();
//            criteria.andEqualTo("deptId",user.getDeptId());
//            devices =deviceMapper.selectByExample(example);
            deviceDto.setDeptId(user.getDeptId());
            devices =deviceMapper.selectDeviceList(deviceDto);
        }
        //管理获取所有摄像机数据中的随机4个
        if(devices.size()>4)
            devices.subList(0,4);
        return devices;
    }

    /**
     * @Method selectDeviceList
     * @Author zhengxin
     * @Description 获取视频设备列表
     * @Param: [deviceDto] 视频设备信息
     * @Return java.util.List<com.yddl.entity.Device>
     * @Date 2020/12/16 11:49
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Device> selectDeviceList(DeviceDto deviceDto) {
        SysUser user=loginUserService.getLoginUser();
        boolean isAdmin = user.isAdmin();
        //是管理员查看所有数据
        if(isAdmin&& deviceDto.getDeptId()==null) {
            return deviceMapper.selectAll();
        }else{
            if(deviceDto.getDeptId()==null)
                //获取本单位下的所有摄像机
                deviceDto.setDeptId(user.getDeptId());
            return deviceMapper.selectDeviceList(deviceDto);
        }
    }

    /**
     * @Method selectCameraToNvr
     * @Author zhengxin
     * @Description 获取已加入硬盘录像机的视频设备列表
     * @Return java.util.List<com.yddl.entity.Device>
     * @Date 2021/4/29 13:59
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Device> selectCameraToNvrList() {
//        List<Device> deviceList=new ArrayList<Device>();
        SysUser user=loginUserService.getLoginUser();
        boolean isAdmin = user.isAdmin();
        List<Device> deviceList=new ArrayList<>();
        //是管理员查看所有数据
        Example example=new Example(Device.class);
        Example.Criteria criteria = example.createCriteria();
        //type为1，表示摄像头已经加入硬盘录像机
        criteria.andEqualTo("type","1");
        if(!isAdmin){
            //获取本单位下的所有摄像机
            DeviceDto deviceDto=new DeviceDto();
            deviceDto.setDeptId(user.getDeptId());
            deviceList =deviceMapper.selectDeviceList(deviceDto);
        }else{
            deviceList = deviceMapper.selectByExample(example);
        }
        for (Device device : deviceList) {
            //数据脱敏
            device.setPassword(null);
        }
        return deviceList;
    }

    /**
     * @Method selectDeviceById
     * @Author zhengxin
     * @Description 根据视频设备id获取详情
     * @Param: [deviceId] 视频设备id
     * @Return com.yddl.entity.Device
     * @Date 2020/12/16 14:23
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Device selectDeviceById(Long deviceId) {
        return deviceMapper.selectByPrimaryKey(deviceId);
    }

    /**
     * @Method checkDeviceCodeUnique
     * @Author zhengxin
     * @Description 设备编码是否唯一
     * @Param: [code] 设备编码
     * @Return boolean
     * @Date 2020/12/16 14:36
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean checkDeviceCodeUnique(String code) {
        Example example=new Example(Device.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code",code);
        List<Device> deviceList = deviceMapper.selectByExample(example);
        if(deviceList.size()>0)
            return true;
        return false;
    }

    /**
     * @Method queryDeviceByCode
     * @Author zhengxin
     * @Description 根据设备编码获取视频设备信息
     * @Param: [code] 设备编码
     * @Return com.yddl.entity.Device
     * @Date 2020/12/21 11:43
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Device queryDeviceByCode(String code) {
        Example example=new Example(Device.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code",code);
        List<Device> deviceList = deviceMapper.selectByExample(example);
        if(deviceList.size()==1)
            return deviceList.get(0);
        return null;
    }

    /**
     * @Method insertDevice
     * @Author zhengxin
     * @Description 新增视频设备
     * @Param: [deviceDto] 视频设备信息
     * @Return int
     * @Date 2020/12/16 14:47
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertDevice(DeviceDto deviceDto) {
        Device device=new Device();
        BeanUtils.copyProperties(deviceDto,device);
        device.setCreateTime(new Date());
        device.setType(DeviceTypeEnum.HK.type);
        return deviceMapper.insert(device);
    }

    /**
     * @Method updateDevice
     * @Author zhengxin
     * @Description 修改视频设备
     * @Param: [deviceDto] 视频设备信息
     * @Return int
     * @Date 2020/12/16 15:16
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateDevice(DeviceDto deviceDto) {
        Device device=new Device();
        BeanUtils.copyProperties(deviceDto,device);
        device.setUpdateTime(new Date());
        return deviceMapper.updateByPrimaryKeySelective(device);
    }

    /**
     * @Method deleteDeviceByIds
     * @Author zhengxin
     * @Description 批量删除视频设备
     * @Param: [deviceIds] 视频设备ID数组
     * @Return int
     * @Date 2020/12/16 15:22
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteDeviceByIds(Long[] deviceIds) {
        return deviceMapper.deleteDeviceByIds(deviceIds);
    }
}
