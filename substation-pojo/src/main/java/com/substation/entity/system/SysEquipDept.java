package com.substation.entity.system;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_equip_dept")
public class SysEquipDept {
    /**
     * 设备ID
     */
    @Id
    @Column(name = "equip_id")
    private Long equipId;

    /**
     * 单位ID
     */
    @Id
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 获取设备ID
     *
     * @return equip_id - 设备ID
     */
    public Long getEquipId() {
        return equipId;
    }

    /**
     * 设置设备ID
     *
     * @param equipId 设备ID
     */
    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    /**
     * 获取单位ID
     *
     * @return dept_id - 单位ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置单位ID
     *
     * @param deptId 单位ID
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
