package com.substation.camera.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: FlowConfig
 * @Author: zhengxin
 * @Description: 读取配置文件的bean
 * @Date: 2021/3/24 13:57
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "config")
public class StreamConfig {

    private String keepalive;// 保活时长（分钟）
    private String push_host;// 推送地址
    private String host_extra;// 额外地址
    private String push_port;// 推送端口
    private String main_code;// 主码流最大码率
    private String sub_code;// 主码流最大码率
    private String version;// 版本信息
    private String public_ip;//公网ip

    public String getHost_extra() {
        return host_extra;
    }

    public void setHost_extra(String host_extra) {
        this.host_extra = host_extra;
    }

    public String getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(String keepalive) {
        this.keepalive = keepalive;
    }

    public String getPush_host() {
        return push_host;
    }

    public void setPush_host(String push_host) {
        this.push_host = push_host;
    }

    public String getPush_port() {
        return push_port;
    }

    public void setPush_port(String push_port) {
        this.push_port = push_port;
    }

    public String getMain_code() {
        return main_code;
    }

    public void setMain_code(String main_code) {
        this.main_code = main_code;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPublic_ip() {
        return public_ip;
    }

    public void setPublic_ip(String public_ip) {
        this.public_ip = public_ip;
    }

    @Override
    public String toString() {
        return "StreamConfig{" +
                "keepalive='" + keepalive + '\'' +
                ", push_host='" + push_host + '\'' +
                ", host_extra='" + host_extra + '\'' +
                ", push_port='" + push_port + '\'' +
                ", main_code='" + main_code + '\'' +
                ", sub_code='" + sub_code + '\'' +
                ", version='" + version + '\'' +
                ", public_ip='" + public_ip + '\'' +
                '}';
    }
}
