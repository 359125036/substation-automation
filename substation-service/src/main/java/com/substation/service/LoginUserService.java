package com.substation.service;

import com.alibaba.fastjson.JSONObject;
import com.substation.entity.system.SysUser;
import com.substation.utils.ConstantUtil;
import com.substation.utils.JwtUtil;
import com.substation.utils.RedisOperator;
import com.substation.utils.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:    LoginUserService
 * Package:    com.yddl.service.system
 * Description: 登录用户缓存信息
 * Datetime:    2020/11/24   14:08
 * Author:   zx
 */
@Component
public class LoginUserService {

    @Autowired
    private RedisOperator redisOperator;


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public SysUser getLoginUser()
    {
        // 获取请求携带的令牌
        String token = getRequestToken((HttpServletRequest) ServletUtils.getRequest());
        if (StringUtils.isNotBlank(token)) {
            // 解密获得userName，用于获取登录缓存的数据
            String userName = JwtUtil.getClaim(token, ConstantUtil.ACCOUNT);
            String  userStr=redisOperator.get(ConstantUtil.PREFIX_USER_CACHE+userName);
            JSONObject jsonObject=JSONObject.parseObject(userStr);
            SysUser user=JSONObject.toJavaObject(jsonObject, SysUser.class);
            return user;
        }
        return null;
    }

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

    /**
     * 获取用户账户
     **/
    public  String getUsername(){
       return getLoginUser().getUserName();
    }
}
