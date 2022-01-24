package com.substation.mapper;

import com.substation.entity.system.SysEquipDept;
import com.substation.my.mapper.MyMapper;

public interface SysEquipDeptMapper extends MyMapper<SysEquipDept> {

    /**
     * @Method deleteEquipDeptByEquipId
     * @Author zhengxin
     * @Description  根据设备id删除设备部门关联
     * @Param: equipId 设备id
     * @Return int
     * @Date 2020/12/4 14:26
     * @Version  1.0
     */
    public int deleteEquipDeptByEquipId(Long equipId);
}
