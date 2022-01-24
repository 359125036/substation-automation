package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import com.substation.entity.system.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:  SysMenuDto
 * @Author: zhengxin
 * @Description: 菜单对象Dto
 * @Date: 2020-11-24 13:53:25
 * @Version: 1.0
 */
@ApiModel(value = "菜单对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class SysMenuDto extends BaseEntity {

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID", name = "menuId")
    private  Long menuId;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", name = "menuName")
    private  String menuName;
    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID", name = "parentId")
    private  Long parentId;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", name = "orderNum")
    private  Integer orderNum;
    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", name = "path")
    private  String path;
    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", name = "component")
    private  String component;
    /**
     * 是否为外链 0：是 ；1：否
     */
    @ApiModelProperty(value = "是否为外链 0：是 ；1：否", name = "isFrame")
    private  Integer isFrame;
    /**
     * 菜单类型 M：目录； C：菜单 ；F：按钮
     */
    @ApiModelProperty(value = "菜单类型 M：目录； C：菜单 ；F：按钮", name = "menuType")
    private  String menuType;
    /**
     * 菜单状态 0：显示； 1：隐藏
     */
    @ApiModelProperty(value = "菜单状态 0：显示； 1：隐藏", name = "visible")
    private  String visible;
    /**
     * 菜单状态 0：正常； 1：停用
     */
    @ApiModelProperty(value = "菜单状态 0：正常； 1：停用", name = "status")
    private  String status;
    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", name = "perms")
    private  String perms;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标", name = "icon")
    private  String icon;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者", name = "createBy")
    private  String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private  Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者", name = "updateBy")
    private  String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updateTime")
    private  Date updateTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private  String remark;


    /** 子菜单 */

    @ApiModelProperty(value = "子菜单", name = "children")
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getIsFrame() {
        return isFrame;
    }

    public void setIsFrame(Integer isFrame) {
        this.isFrame = isFrame;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", menuId=").append(menuId);
        sb.append(", menuName=").append(menuName);
        sb.append(", parentId=").append(parentId);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", path=").append(path);
        sb.append(", component=").append(component);
        sb.append(", isFrame=").append(isFrame);
        sb.append(", menuType=").append(menuType);
        sb.append(", visible=").append(visible);
        sb.append(", status=").append(status);
        sb.append(", perms=").append(perms);
        sb.append(", icon=").append(icon);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }

}
