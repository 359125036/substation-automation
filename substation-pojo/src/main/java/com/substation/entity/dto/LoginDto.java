package com.substation.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: LoginDto
 * @Author: zhengxin
 * @Description: 用户登录对象
 * @Date: 2020/11/16 15:53
 * @Version: 1.0
 */
@ApiModel(value = "登录对象Dto", description = "从客户端，由用户传入的数据封装在此entity中")
public class LoginDto {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", name = "username")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", name = "password")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", name = "code")
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "唯一标识", name = "uuid")
    private String uuid = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
