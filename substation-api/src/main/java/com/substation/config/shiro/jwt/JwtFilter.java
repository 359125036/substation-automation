package com.substation.config.shiro.jwt;

import com.alibaba.fastjson.JSONObject;
import com.substation.config.shiro.ShiroParam;
import com.substation.http.HttpResult;
import com.substation.http.HttpStatus;
import com.substation.utils.ConstantUtil;
import com.substation.utils.JwtUtil;
import com.substation.utils.RedisOperator;
import com.substation.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: JwtFilter
 * @Author: zhengxin
 * @Description: JWT过滤
 * @Date: 2020/11/15 10:58
 * @Version: 1.0
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response)  {
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            return null;
        }
        return new JwtToken(token);
    }
    /**
     * @Method isAccessAllowed
     * @Author zhengxin
     * @Description 在登录的情况下会走此方法，此方法返回true直接访问控制器
     * @param request
     * @param response
     * @param mappedValue
     * @Return boolean
     * @Exception
     * @Date 2020/11/15 11:00
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        // 解密获得userName
        String userName = JwtUtil.getClaim(token, ConstantUtil.ACCOUNT);
        //token过期或不存在是走onAccessDenied
        if(StringUtils.isBlank(SpringUtils.getBean(RedisOperator.class).get(ConstantUtil.PREFIX_SHIRO_REFRESH_TOKEN + userName))||
                SpringUtils.getBean(RedisOperator.class).ttl(ConstantUtil.PREFIX_SHIRO_REFRESH_TOKEN + userName)<1){
            return false;
        }
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        token = JwtUtil.sign(userName, currentTimeMillis);
        SpringUtils.getBean(RedisOperator.class).set(ConstantUtil.PREFIX_SHIRO_REFRESH_TOKEN + userName, token, Integer.parseInt(SpringUtils.getBean(ShiroParam.class).getRefreshTokenExpireTime()));
        //更新token
        SpringUtils.getBean(RedisOperator.class).expire(ConstantUtil.PREFIX_SHIRO_REFRESH_TOKEN + userName,Integer.parseInt(SpringUtils.getBean(ShiroParam.class).getRefreshTokenExpireTime()));
        //更新登录用户过期时间
        SpringUtils.getBean(RedisOperator.class).expire(ConstantUtil.PREFIX_USER_CACHE + userName,Integer.parseInt(SpringUtils.getBean(ShiroParam.class).getRefreshTokenExpireTime()));
        return true;
    }

    /**
     * @Method onAccessDenied
     * @Author zhengxin
     * @Description 没有登录的情况下会走此方法,即isAccessAllowed为false进入该方法
     * @param request
     * @param response
     * @Return boolean
     * @Exception
     * @Date 2020/11/15 11:09
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpResult result = HttpResult.error(HttpStatus.SC_UNAUTHORIZED, "invalid token");
            String json = JSONObject.toJSONString(result);
            httpResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }
//
//    /**
//     * 进行AccessToken登录认证授权
//     */
//    @Override
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
//        JwtToken token = new JwtToken(this.getAuthzHeader(request));
//        // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
//        this.getSubject(request, response).login(token);
//        // 如果没有抛出异常则代表登入成功，返回true
//        return true;
//    }
    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        // 从header中获取token
        String token = httpRequest.getHeader("Authorization");
        // 如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("Authorization");
        }
        return token;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json; charset=utf-8");
        try {
            // 处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            HttpResult result = HttpResult.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());
            String json = JSONObject.toJSONString(result);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
        }
        return false;
    }

}
