package com.substation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: WebMvcConfig
 * @Author: zhengxin
 * @Description: 将RestTemplate初始化为bean
 * @Date: 2020/5/23 17:48
 * @Version: 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * @Method addResourceHandlers
     * @Author zhengxin
     * @Description 映射资源到项目路径，实现网页访问
     * @param registry
     * @Return void
     * @Exception
     * @Date 2020/5/30 8:33
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")//所有路径
                .addResourceLocations("classpath:/META-INF/resources/")//映射swagger2
                .addResourceLocations("file:/workspaces/images/");//映射本地静态资源
    }

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder){
//        return builder.build();
//    }
//
//    /**
//     * @Method userTokenInterceptor
//     * @Author zhengxin
//     * @Description 用户分布式会话拦截器添加到spring管理器中
//     * @param
//     * @Return com.zx.controller.interceptor.UserTokenInterceptor
//     * @Exception
//     * @Date 2020/9/21 12:08
//     */
//    @Bean
//    public UserTokenInterceptor userTokenInterceptor(){
//        return new UserTokenInterceptor();
//    }
//
//    /**
//     * @Method addInterceptors
//     * @Author zhengxin
//     * @Description 注册拦截器
//     * @param registry
//     * @Return void
//     * @Exception
//     * @Date 2020/9/21 12:10
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(userTokenInterceptor())
//                .addPathPatterns("/queryHello")
////                .addPathPatterns("/project/*")//验证是否项目模块是否有token
//                .addPathPatterns("/decoration/*")//验证是否装修模块是否有token
//                .addPathPatterns("/img/*")//验证是否图片模块是否有token
//                .excludePathPatterns("/myorders/deliver")
//                .excludePathPatterns("/orders/notifyMerchantOrderPaid");
//
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
}
