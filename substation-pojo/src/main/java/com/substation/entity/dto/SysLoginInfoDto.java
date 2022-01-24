package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  SysLoginInfoDto
 * @Author: zhengxin
 * @Description: 登录记录对象Dto
 * @Date: 2020-11-25 15:05:07
 * @Version: 1.0
 */
@ApiModel(value = "登录记录对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class SysLoginInfoDto extends BaseEntity {

    /**
     * 访问ID
     */
    @ApiModelProperty(value = "访问ID", name = "infoId")
    private Integer infoId;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", name = "userName")
    private String userName;

    /**
     * 登录IP地址
     */
    @ApiModelProperty(value = "登录IP地址", name = "ipAddr")
    private String ipAddr;

    /**
     * 登录地点
     */
    @ApiModelProperty(value = "登录地点", name = "loginLocation")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @ApiModelProperty(value = "浏览器类型", name = "browser")
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统", name = "os")
    private String os;

    /**
     * 登录状态 0：成功 ；1：失败
     */
    @ApiModelProperty(value = "登录状态 0：成功 ；1：失败", name = "status")
    private String status;

    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息", name = "msg")
    private String msg;

    /**
     * 访问时间
     */
    @ApiModelProperty(value = "访问时间", name = "loginTime")
    private Date loginTime;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", infoId=").append(infoId);
        sb.append(", userName=").append(userName);
        sb.append(", ipAddr=").append(ipAddr);
        sb.append(", loginLocation=").append(loginLocation);
        sb.append(", browser=").append(browser);
        sb.append(", os=").append(os);
        sb.append(", status=").append(status);
        sb.append(", msg=").append(msg);
        sb.append(", loginTime=").append(loginTime);
        sb.append("]");
        return sb.toString();
    }

}
