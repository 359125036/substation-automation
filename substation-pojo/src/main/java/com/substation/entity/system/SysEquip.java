package com.substation.entity.system;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "sys_equip")
public class SysEquip {
    /**
     * 设备ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equip_id")
    private Long equipId;

    /**
     * 父设备ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 设备名称
     */
    @Column(name = "equip_name")
    private String equipName;

    /**
     * 显示顺序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 设备唯一编号
     */
    private String code;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 设备状态 0：正常； 1：停用
     */
    private String status;

    /**
     * 删除标志 0：代表存在 ；2：代表删除
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;


    /** 子部门 */
    private List<SysEquip> children = new ArrayList<SysEquip>();


    public List<SysEquip> getChildren() {
        return children;
    }

    public void setChildren(List<SysEquip> children) {
        this.children = children;
    }

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
     * 获取父设备ID
     *
     * @return parent_id - 父设备ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父设备ID
     *
     * @param parentId 父设备ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取祖级列表
     *
     * @return ancestors - 祖级列表
     */
    public String getAncestors() {
        return ancestors;
    }

    /**
     * 设置祖级列表
     *
     * @param ancestors 祖级列表
     */
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    /**
     * 获取设备名称
     *
     * @return equip_name - 设备名称
     */
    public String getEquipName() {
        return equipName;
    }

    /**
     * 设置设备名称
     *
     * @param equipName 设备名称
     */
    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    /**
     * 获取显示顺序
     *
     * @return order_num - 显示顺序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置显示顺序
     *
     * @param orderNum 显示顺序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取设备唯一编号
     *
     * @return code - 设备唯一编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置设备唯一编号
     *
     * @param code 设备唯一编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取负责人
     *
     * @return leader - 负责人
     */
    public String getLeader() {
        return leader;
    }

    /**
     * 设置负责人
     *
     * @param leader 负责人
     */
    public void setLeader(String leader) {
        this.leader = leader;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取设备状态 0：正常； 1：停用
     *
     * @return status - 设备状态 0：正常； 1：停用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置设备状态 0：正常； 1：停用
     *
     * @param status 设备状态 0：正常； 1：停用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取删除标志 0：代表存在 ；2：代表删除
     *
     * @return del_flag - 删除标志 0：代表存在 ；2：代表删除
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标志 0：代表存在 ；2：代表删除
     *
     * @param delFlag 删除标志 0：代表存在 ；2：代表删除
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取创建者
     *
     * @return create_by - 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者
     *
     * @param createBy 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新者
     *
     * @return update_by - 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新者
     *
     * @param updateBy 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
