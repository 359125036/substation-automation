package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  SysOperLogDto
 * @Author: zhengxin
 * @Description: 操作日志对象Dto
 * @Date: 2020-11-25 10:23:11
 * @Version: 1.0
 */
@ApiModel(value = "操作日志对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class SysOperLogDto extends BaseEntity {

    /**
     * 日志主键
     */
    @ApiModelProperty(value = "日志主键", name = "operId")
    private String operId;

    /**
     * 模块标题
     */
    @ApiModelProperty(value = "模块标题", name = "title")
    private String title;

    /**
     * 业务类型 0：其它； 1：新增； 2：修改； 3：删除
     */
    @ApiModelProperty(value = "业务类型 0：其它； 1：新增； 2：修改； 3：删除", name = "businessType")
    private Integer businessType;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称", name = "method")
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", name = "requestMethod")
    private String requestMethod;

    /**
     * 操作类别 0：其它 ；1：后台用户 ；2：手机端用户
     */
    @ApiModelProperty(value = "操作类别 0：其它 ；1：后台用户 ；2：手机端用户", name = "operatorType")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员", name = "operName")
    private String operName;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", name = "deptName")
    private String deptName;

    /**
     * 请求URL
     */
    @ApiModelProperty(value = "请求URL", name = "operUrl")
    private String operUrl;

    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址", name = "operIp")
    private String operIp;

    /**
     * 操作地点
     */
    @ApiModelProperty(value = "操作地点", name = "operLocation")
    private String operLocation;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", name = "operParam")
    private String operParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数", name = "jsonResult")
    private String jsonResult;

    /**
     * 操作状态 0：正常； 1：异常
     */
    @ApiModelProperty(value = "操作状态 0：正常； 1：异常", name = "status")
    private Integer status;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息", name = "errorMsg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间", name = "operTime")
    private Date operTime;

    /** 业务类型数组 */
    @ApiModelProperty(value = "业务类型数组", name = "businessTypes")
    private Integer[] businessTypes;

    public Integer[] getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(Integer[] businessTypes) {
        this.businessTypes = businessTypes;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public String getOperLocation() {
        return operLocation;
    }

    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation;
    }

    public String getOperParam() {
        return operParam;
    }

    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", operId=").append(operId);
        sb.append(", title=").append(title);
        sb.append(", businessType=").append(businessType);
        sb.append(", method=").append(method);
        sb.append(", requestMethod=").append(requestMethod);
        sb.append(", operatorType=").append(operatorType);
        sb.append(", operName=").append(operName);
        sb.append(", deptName=").append(deptName);
        sb.append(", operUrl=").append(operUrl);
        sb.append(", operIp=").append(operIp);
        sb.append(", operLocation=").append(operLocation);
        sb.append(", operParam=").append(operParam);
        sb.append(", jsonResult=").append(jsonResult);
        sb.append(", status=").append(status);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", operTime=").append(operTime);
        sb.append("]");
        return sb.toString();
    }

}
