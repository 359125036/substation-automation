package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  SysPostDto
 * @Author: zhengxin
 * @Description: 岗位对象Dto
 * @Date: 2020-11-24 18:34:06
 * @Version: 1.0
 */
@ApiModel(value = "岗位对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class SysPostDto extends BaseEntity {

    /**
     * 岗位ID
     */
    @ApiModelProperty(value = "岗位ID", name = "postId")
    private Long postId;

    /**
     * 岗位编码
     */
    @ApiModelProperty(value = "岗位编码", name = "postCode")
    private String postCode;

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称", name = "postName")
    private String postName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", name = "postSort")
    private Integer postSort;

    /**
     * 状态 0：正常； 1：停用
     */
    @ApiModelProperty(value = "状态 0：正常； 1：停用", name = "status")
    private String status;

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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }


    public Integer getPostSort() {
        return postSort;
    }

    public void setPostSort(Integer postSort) {
        this.postSort = postSort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        sb.append(", postId=").append(postId);
        sb.append(", postCode=").append(postCode);
        sb.append(", postName=").append(postName);
        sb.append(", postSort=").append(postSort);
        sb.append(", status=").append(status);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }

}
