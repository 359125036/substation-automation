package com.substation.config.shiro;

import com.substation.config.shiro.jwt.JwtFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @ClassName: ShiroConfig
 * @Author: zhengxin
 * @Description: Shiro配置
 * @Date: 2020/11/14 22:06
 * @Version: 1.0
 */
@Configuration
public class ShiroConfig {

    /**
     * @Method shirFilter
     * @Author zhengxin
     * @Description shiro方法拦截器
     * @param securityManager
     * @Return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @Exception
     * @Date 2020/11/15 11:47
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        // 自定义 JwtFilter 过滤器，替代默认的过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", new JwtFilter());
        shiroFilter.setFilters(filters);
        // 访问路径拦截配置，"anon"表示无需验证，未登录也可访问
        Map<String, String> filterMap = new LinkedHashMap<>();
        // Swagger接口文档
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/doc.html", "anon");
        // 公开接口
        // filterMap.put("/api/**", "anon");
        // 登录接口放开
        filterMap.put("/login", "anon");
//        filterMap.put("/system/*", "anon");
        // 其他所有路径交给JwtFilter处理
        filterMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean
    public Realm getShiroRealm(){
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        // 注入 Realm 实现类，实现自己的登录逻辑
        securityManager.setRealm(getShiroRealm());
        return securityManager;
    }
}
