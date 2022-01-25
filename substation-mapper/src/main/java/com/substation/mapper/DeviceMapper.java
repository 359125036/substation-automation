package com.substation.mapper;

import com.substation.entity.Device;
import com.substation.entity.dto.DeviceDto;
import com.substation.my.mapper.MyMapper;

import java.util.List;

public interface DeviceMapper extends MyMapper<Device> {

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
