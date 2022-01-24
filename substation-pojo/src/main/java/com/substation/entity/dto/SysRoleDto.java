package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  SysRoleDto
 * @Author: zhengxin
 * @Description: 角色对象Dto
 * @Date: 2020-11-24 17:23:25
 * @Version: 1.0
 */
@ApiModel(value = "角色对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class SysRoleDto extends BaseEntity {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", name = "roleId")
    private Long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", name = "roleName")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @ApiModelProperty(value = "角色权限字符串", name = "roleKey")
    private String roleKey;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", name = "roleSort")
    private Integer roleSort;

    /**
     * 数据范围 1：全部数据权限； 2：自定数据权限； 3：本部门数据权限； 4：本部门及以下数据权限
     */
    @ApiModelProperty(value = "数据范围 1：全部数据权限； 2：自定数据权限； 3：本部门数据权限； 4：本部门及以下数据权限", name = "dataScope")
    private String dataScope;

    /**
     * 角色状态 0：正常； 1：停用
     */
    @ApiModelProperty(value = "角色状态 0：正常； 1：停用", name = "status")
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

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;



    /** 菜单ID组 */
    @ApiModelProperty(value = "菜单ID组 ", name = "menuIds")
    private transient Long[] menuIds;

    /** 部门ID组（数据权限） */
    @ApiModelProperty(value = "部门ID组 ", name = "deptIds")
    private  transient Long[] deptIds;

    public Long[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds) {
        this.menuIds = menuIds;
    }

    public Long[] getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(Long[] deptIds) {
        this.deptIds = deptIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", roleKey=").append(roleKey);
        sb.append(", roleSort=").append(roleSort);
        sb.append(", dataScope=").append(dataScope);
        sb.append(", status=").append(status);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }

}
