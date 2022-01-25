package com.substation.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "video_device")
public class Device {
    /**
     * 视频设备ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 账号
     */
    private String account;

    /**
     * 设备名称
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 密码
     */
    private String password;

    /**
     * 硬盘录像机端口
     */
    @Column(name = "dvr_port")
    private String dvrPort;

    /**
     * 端口
     */
    private String port;

    /**
     * 硬盘录像机转向端口
     */
    @Column(name = "dvr_direction_port")
    private String dvrDirectionPort;

    /**
     * 转向端口
     */
    @Column(name = "direction_port")
    private String directionPort;

    /**
     * 硬盘录像机ip
     */
    @Column(name = "dvr_ip")
    private String dvrIp;

    /**
     * ip
     */
    private String ip;

    /**
     * 设备编码（序列号）
     */
    private String code;

    /**
     * 类型
     */
    private String type;

    /**
     * 状态 0：正常； 1：停用
     */
    private String status;

    /**
     * 通道
     */
    private String channel;

    /**
     * 摄像头位置
     */
    private String address;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 修改者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取视频设备ID
     *
     * @return device_id - 视频设备ID
     */
    public Long getDeviceId() {
        return deviceId;
    }

    /**
     * 设置视频设备ID
     *
     * @param deviceId 视频设备ID
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取部门ID
     *
     * @return dept_id - 部门ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置部门ID
     *
     * @param deptId 部门ID
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取设备名称
     *
     * @return device_name - 设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置设备名称
     *
     * @param deviceName 设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取硬盘录像机端口
     *
     * @return dvr_port - 硬盘录像机端口
     */
    public String getDvrPort() {
        return dvrPort;
    }

    /**
     * 设置硬盘录像机端口
     *
     * @param dvrPort 硬盘录像机端口
     */
    public void setDvrPort(String dvrPort) {
        this.dvrPort = dvrPort;
    }

    /**
     * 获取端口
     *
     * @return port - 端口
     */
    public String getPort() {
        return port;
    }

    /**
     * 设置端口
     *
     * @param port 端口
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 获取硬盘录像机转向端口
     *
     * @return dvr_direction_port - 硬盘录像机转向端口
     */
    public String getDvrDirectionPort() {
        return dvrDirectionPort;
    }

    /**
     * 设置硬盘录像机转向端口
     *
     * @param dvrDirectionPort 硬盘录像机转向端口
     */
    public void setDvrDirectionPort(String dvrDirectionPort) {
        this.dvrDirectionPort = dvrDirectionPort;
    }

    /**
     * 获取转向端口
     *
     * @return direction_port - 转向端口
     */
    public String getDirectionPort() {
        return directionPort;
    }

    /**
     * 设置转向端口
     *
     * @param directionPort 转向端口
     */
    public void setDirectionPort(String directionPort) {
        this.directionPort = directionPort;
    }

    /**
     * 获取硬盘录像机ip
     *
     * @return dvr_ip - 硬盘录像机ip
     */
    public String getDvrIp() {
        return dvrIp;
    }

    /**
     * 设置硬盘录像机ip
     *
     * @param dvrIp 硬盘录像机ip
     */
    public void setDvrIp(String dvrIp) {
        this.dvrIp = dvrIp;
    }

    /**
     * 获取ip
     *
     * @return ip - ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip
     *
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取设备编码（序列号）
     *
     * @return code - 设备编码（序列号）
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置设备编码（序列号）
     *
     * @param code 设备编码（序列号）
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取状态 0：正常； 1：停用
     *
     * @return status - 状态 0：正常； 1：停用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 0：正常； 1：停用
     *
     * @param status 状态 0：正常； 1：停用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取通道
     *
     * @return channel - 通道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置通道
     *
     * @param channel 通道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取摄像头位置
     *
     * @return address - 摄像头位置
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置摄像头位置
     *
     * @param address 摄像头位置
     */
    public void setAddress(String address) {
        this.address = address;
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
     * 获取修改者
     *
     * @return update_by - 修改者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改者
     *
     * @param updateBy 修改者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
