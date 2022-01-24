package com.substation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: Application
 * @Description: 启动类
 * @Author: zhengxin
 * @Date: 2020/11/11 11:44
 * @Version: 1.0
 */

@SpringBootApplication
//扫描mybatis通用所在的包
@MapperScan(basePackages = "com.substation.mapper")
//// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.substation"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}

