package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  SysConfigDto
 * @Author: zhengxin
 * @Description: 系统配置对象Dto
 * @Date: 2020-11-25 09:24:49
 * @Version: 1.0
 */
@ApiModel(value = "系统配置对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class SysConfigDto extends BaseEntity {

    /**
     * 参数主键
     */
    @ApiModelProperty(value = "参数主键", name = "configId")
    private Integer configId;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称", name = "configName")
    private String configName;

    /**
     * 参数键名
     */
    @ApiModelProperty(value = "参数键名", name = "configKey")
    private String configKey;

    /**
     * 参数键值
     */
    @ApiModelProperty(value = "参数键值", name = "configValue")
    private String configValue;

    /**
     * 系统内置 Y：是； N：否
     */
    @ApiModelProperty(value = "系统内置 Y：是； N：否", name = "configType")
    private String configType;

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

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
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
        sb.append(", configId=").append(configId);
        sb.append(", configName=").append(configName);
        sb.append(", configKey=").append(configKey);
        sb.append(", configValue=").append(configValue);
        sb.append(", configType=").append(configType);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }

}
