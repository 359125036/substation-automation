package com.substation.mapper;

import com.substation.entity.dto.SysEquipDto;
import com.substation.entity.system.SysEquip;
import com.substation.my.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysEquipMapper extends MyMapper<SysEquip> {
    /**
     * @Method selectDeptList
     * @Author zhengxin
     * @Description 查询设备管理数据
     * @Param: [sysEquip] 设备信息
     * @Return java.util.List<SysEquip>
     * @Date 2020/12/4 11:20
     * @Version  1.0
     */
    public List<SysEquip> selectEquipList(SysEquipDto sysEquip);

    /**
     * @Method selectChildrenEquipById
     * @Author zhengxin
     * @Description 根据设备ID查询所有子设备
     * @Param: [equipId] 设备id
     * @Return java.util.List<SysEquip>
     * @Date 2020/12/4 11:20
     * @Version  1.0
     */
    public List<SysEquip> selectChildrenEquipById(Long equipId);

    /**
     * @Method selectChildrenEquipById
     * @Author zhengxin
     * @Description 修改子元素关系
     * @Param: [equipId] 子元素
     * @Return java.util.List<SysEquip>
     * @Date 2020/12/4 11:20
     * @Version  1.0
     */
    public int updateEquipChildren(@Param("equips") List<SysEquip> equips);

    /**
     * @Method updateDeptStatus
     * @Author zhengxin
     * @Description 更新设备状态
     * @Param: [equip] 设备信息
     * @Return java.util.List<SysEquip>
     * @Date 2020/12/4 11:20
     * @Version  1.0
     */
    public void updateEquipStatus(SysEquip equip);
}
