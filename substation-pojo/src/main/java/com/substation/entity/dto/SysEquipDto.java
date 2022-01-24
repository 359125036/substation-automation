package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  SysEquipDto
 * @Author: zhengxin
 * @Description: 设备对象Dto
 * @Date: 2020-12-04 11:12:53
 * @Version: 1.0
 */
@ApiModel(value = "设备对象Dto", description = "从客户端，由电容传入的数据封装在此entity中")
public class SysEquipDto extends BaseEntity {

    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID", name = "equipId")
    private Long equipId;

    /**
     * 父设备ID
     */
    @ApiModelProperty(value = "父设备ID", name = "parentId")
    private Long parentId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", name = "deptId")
    private Long deptId;

    /**
     * 祖级列表
     */
    @ApiModelProperty(value = "祖级列表", name = "ancestors")
    private String ancestors;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称", name = "equipName")
    private String equipName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", name = "orderNum")
    private Integer orderNum;

    /**
     * 设备唯一编号
     */
    @ApiModelProperty(value = "设备唯一编号", name = "code")
    private String code;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人", name = "leader")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", name = "phone")
    private String phone;

    /**
     * 设备状态 0：正常； 1：停用
     */
    @ApiModelProperty(value = "设备状态 0：正常； 1：停用", name = "status")
    private String status;

    /**
     * 删除标志 0：代表存在 ；2：代表删除
     */
    @ApiModelProperty(value = "删除标志 0：代表存在 ；2：代表删除", name = "delFlag")
    private String delFlag;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者", name = "createBy")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者", name = "updateBy")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updateTime")
    private Date updateTime;

    public Long getEquipId() {
        return equipId;
    }

    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", equipId=").append(equipId);
        sb.append(", parentId=").append(parentId);
        sb.append(", ancestors=").append(ancestors);
        sb.append(", equipName=").append(equipName);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", code=").append(code);
        sb.append(", leader=").append(leader);
        sb.append(", phone=").append(phone);
        sb.append(", status=").append(status);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}
