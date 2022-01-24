package com.substation.config.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ShiroParam
 * @Author: zhengxin
 * @Description: 获取配置文件中的参数
 * @Date: 2020/11/21 10:27
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "shiro")//配置文件中的前缀
@PropertySource("classpath:shiro-config.properties")
public class ShiroParam {

    //token过期时长(s)
    public  String accessTokenExpireTime;

    //刷新token过期时间
    public  String refreshTokenExpireTime;

    public String getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    public String getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(String refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }
}
