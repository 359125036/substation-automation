package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  DeviceDto
 * @Author: zhengxin
 * @Description: 视频设备对象对象Dto
 * @Date: 2021-01-13 17:55:31
 * @Version: 1.0
 */
@ApiModel(value = "视频设备对象对象Dto", description = "从客户端，由视频设备对象传入的数据封装在此entity中")
public class DeviceDto extends BaseEntity {

    /**
     * 视频设备ID
     */
    @ApiModelProperty(value = "视频设备ID", name = "deviceId")
    private Long deviceId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", name = "deptId")
    private Long deptId;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", name = "account")
    private String account;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称", name = "deviceName")
    private String deviceName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "password")
    private String password;

    /**
     * 硬盘录像机端口
     */
    @ApiModelProperty(value = "硬盘录像机端口", name = "dvrPort")
    private String dvrPort;

    /**
     * 端口
     */
    @ApiModelProperty(value = "端口", name = "port")
    private String port;

    /**
     * 硬盘录像机转向端口
     */
    @ApiModelProperty(value = "硬盘录像机转向端口", name = "dvrDirectionPort")
    private String dvrDirectionPort;

    /**
     * 转向端口
     */
    @ApiModelProperty(value = "转向端口", name = "directionPort")
    private String directionPort;

    /**
     * 硬盘录像机ip
     */
    @ApiModelProperty(value = "硬盘录像机ip", name = "dvrIp")
    private String dvrIp;

    /**
     * ip
     */
    @ApiModelProperty(value = "ip", name = "ip")
    private String ip;

    /**
     * 设备编码（序列号）
     */
    @ApiModelProperty(value = "设备编码（序列号）", name = "code")
    private String code;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", name = "type")
    private String type;

    /**
     * 状态 0：正常； 1：停用
     */
    @ApiModelProperty(value = "状态 0：正常； 1：停用", name = "status")
    private String status;

    /**
     * 通道
     */
    @ApiModelProperty(value = "通道", name = "channel")
    private String channel;

    /**
     * 摄像头位置
     */
    @ApiModelProperty(value = "摄像头位置", name = "address")
    private String address;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者", name = "createBy")
    private String createBy;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者", name = "updateBy")
    private String updateBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", name = "updateTime")
    private Date updateTime;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDvrPort() {
        return dvrPort;
    }

    public void setDvrPort(String dvrPort) {
        this.dvrPort = dvrPort;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDvrDirectionPort() {
        return dvrDirectionPort;
    }

    public void setDvrDirectionPort(String dvrDirectionPort) {
        this.dvrDirectionPort = dvrDirectionPort;
    }

    public String getDirectionPort() {
        return directionPort;
    }

    public void setDirectionPort(String directionPort) {
        this.directionPort = directionPort;
    }

    public String getDvrIp() {
        return dvrIp;
    }

    public void setDvrIp(String dvrIp) {
        this.dvrIp = dvrIp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", deviceId=").append(deviceId);
        sb.append(", deptId=").append(deptId);
        sb.append(", account=").append(account);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", password=").append(password);
        sb.append(", dvrPort=").append(dvrPort);
        sb.append(", port=").append(port);
        sb.append(", dvrDirectionPort=").append(dvrDirectionPort);
        sb.append(", directionPort=").append(directionPort);
        sb.append(", dvrIp=").append(dvrIp);
        sb.append(", ip=").append(ip);
        sb.append(", code=").append(code);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", channel=").append(channel);
        sb.append(", address=").append(address);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}
