package com.substation.config.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: JwtToken
 * @Author: zhengxin
 * @Description: jwtToken
 * @Date: 2020/11/9 16:42
 * @Version: 1.0
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    /**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
