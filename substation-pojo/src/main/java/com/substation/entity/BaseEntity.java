package com.substation.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * ClassName:    BaseEntity
 * Package:    com.yddl.entity
 * Description: Entity基类
 * Datetime:    2020/11/24   17:24
 * Author:   zx
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 开始时间 */
    private String beginTime;

    /** 结束时间 */
    private String endTime;

    /** 请求参数 */
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
