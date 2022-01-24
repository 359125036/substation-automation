package com.substation.config.shiro;

import com.substation.config.shiro.jwt.JwtToken;
import com.substation.entity.system.SysUser;
import com.substation.mapper.SysUserMapper;
import com.substation.utils.ConstantUtil;
import com.substation.utils.JwtUtil;
import com.substation.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CustomRealm
 * @Author: zhengxin
 * @Description: 自定义realm
 * @Date: 2020/11/9 15:42
 * @Version: 1.0
 */

@Service
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 判断token是否事我们的这个jwttoekn
     * 注意：未加入该方法就会导致找不到Realm来处理这个Token服务
     */

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * @Method doGetAuthorizationInfo
     * @Author zhengxin
     * @Description 授权
     * @param principalCollection
     * @Return org.apache.shiro.authz.AuthorizationInfo
     * @Exception
     * @Date 2020/11/9 15:58
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String userName = JwtUtil.getClaim(principalCollection.toString(), ConstantUtil.ACCOUNT);
        SysUser user=new SysUser();
        user.setUserName(userName);
        // 查询用户角色
//        List<RoleDto> roleDtos = roleMapper.findRoleByUser(user);
//        for (RoleDto roleDto : roleDtos) {
//            if (roleDto != null) {
//                // 添加角色
//                simpleAuthorizationInfo.addRole(roleDto.getName());
//                // 根据用户角色查询权限
//                List<PermissionDto> permissionDtos = permissionMapper.findPermissionByRole(roleDto);
//                for (PermissionDto permissionDto : permissionDtos) {
//                    if (permissionDto != null) {
//                        // 添加权限
//                        simpleAuthorizationInfo.addStringPermission(permissionDto.getPerCode());
//                    }
//                }
//            }
//        }
        return simpleAuthorizationInfo;
    }


    /**
     * @Method doGetAuthenticationInfo
     * @Author zhengxin
     * @Description 认证
     * @param authenticationToken  体传过来的认证信息
     * @Return org.apache.shiro.authc.AuthenticationInfo
     * @Exception
     * @Date 2020/11/9 15:57
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获取用户名
//        String userName= (String) authenticationToken.getPrincipal();
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String userName = JwtUtil.getClaim(token, ConstantUtil.ACCOUNT);
        // 帐号为空
        if (StringUtils.isBlank(userName)) {
            throw new AuthenticationException("Token中帐号为空(The userName in Token is empty.)");
        }
        // 查询用户是否存在
        SysUser user=new SysUser();
        user.setUserName(userName);
//        user=userMapper.selectOne(user);
//        if (user == null) {
//            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
//        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && redisOperator.existKey(ConstantUtil.PREFIX_SHIRO_REFRESH_TOKEN +  userName)) {
            return new SimpleAuthenticationInfo(token, token, "customRealm");
//            // 获取RefreshToken的时间戳
//            String currentTimeMillisRedis = redisOperator.get(ConstantUtil.PREFIX_SHIRO_REFRESH_TOKEN + userName).toString();
//            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
//            if (JwtUtil.getClaim(token, ConstantUtil.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
//
//            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");

    }
}
