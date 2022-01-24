package com.substation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Swagger2
 * @Description: 生成api接口文档
 * @Author: zhengxin
 * @Date: 2020/10/15 9:21
 * @Version: 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    // http://localhost:8089/swagger-ui.html  swagger原网页
    //http://localhost:8089/doc.html          bootstrap渲染过的页面
    //配置swagger的核心配置docket

    @Bean
    public Docket createRestApi() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name("token").description("令牌")
            .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型为swagger2
                .apiInfo(apiInfo())                 // 用于定义api文档汇总信息
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.substation.controller"))   // 指定controller包
                .paths(PathSelectors.any())         // 所有controller
                .build().globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                    .title("变电站自动化api")  //文档页标题
                    //联系人信息
                    .contact(new Contact("zx","http://localhost:8089/","359125036@qq.com"))
                    //详细信息
                    .description("变电站自动化平台提供的接口")
                    .version("1.0.1")//版本号
                    .termsOfServiceUrl("http://localhost:8089/")
                    .build();
    }

}
