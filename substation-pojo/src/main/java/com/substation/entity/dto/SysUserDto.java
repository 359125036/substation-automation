package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import com.substation.entity.system.SysDept;
import com.substation.entity.system.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:  SysUserDto
 * @Author: zhengxin
 * @Description: 用户对象Dto
 * @Date: 2020-11-24 13:56:06
 * @Version: 1.0
 */
@ApiModel(value = "用户对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class SysUserDto extends BaseEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", name = "userId")
    private Long userId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", name = "deptId")
    private Long deptId;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", name = "userName")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "nickName")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty(value = "用户类型（00系统用户）", name = "userType")
    private String userType;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", name = "email")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", name = "phoneNumber")
    private String phoneNumber;

    /**
     * 用户性别 0：男 ；1：女 ；2：未知
     */
    @ApiModelProperty(value = "用户性别 0：男 ；1：女 ；2：未知", name = "sex")
    private String sex;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址", name = "avatar")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "password")
    private String password;

    /**
     * 帐号状态 0：正常 ；1：停用
     */
    @ApiModelProperty(value = "帐号状态 0：正常 ；1：停用", name = "status")
    private String status;

    /**
     * 删除标志 0：代表存在；2：代表删除
     */
    @ApiModelProperty(value = "删除标志 0：代表存在；2：代表删除", name = "delFlag")
    private String delFlag;

    /**
     * 最后登陆IP
     */
    @ApiModelProperty(value = "最后登陆IP", name = "loginIp")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @ApiModelProperty(value = "最后登陆时间", name = "loginDate")
    private Date loginDate;

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

    /** 部门对象 */
    @ApiModelProperty(value = "部门对象", name = "dept")
    private SysDept dept;

    /** 角色对象 */
    @ApiModelProperty(value = "角色对象集合", name = "roles")
    private List<SysRole> roles;

    /** 角色ID组 */
    @ApiModelProperty(value = "角色ID组", name = "roleIds")
    private Long[] roleIds;

    /** 岗位ID组 */
    @ApiModelProperty(value = "岗位ID组", name = "postIds")
    private Long[] postIds;


    public SysUserDto(Long userId) {
    }

    public SysUserDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
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

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", deptId=").append(deptId);
        sb.append(", userName=").append(userName);
        sb.append(", nickName=").append(nickName);
        sb.append(", userType=").append(userType);
        sb.append(", email=").append(email);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", sex=").append(sex);
        sb.append(", avatar=").append(avatar);
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", loginDate=").append(loginDate);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }

}