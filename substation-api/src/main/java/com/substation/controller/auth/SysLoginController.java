package com.substation.controller.auth;


import com.substation.config.shiro.ShiroParam;
import com.substation.entity.dto.LoginDto;
import com.substation.entity.system.SysMenu;
import com.substation.entity.system.SysUser;
import com.substation.entity.vo.RouterVo;
import com.substation.exception.ValidatorException;
import com.substation.http.HttpResult;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysMenuService;
import com.substation.service.system.ISysRoleService;
import com.substation.service.system.ISysUserService;
import com.substation.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: AuthorizationController
 * @Author: zhengxin
 * @Description: 授权、根据token获取用户详细信息
 * @Date: 2020/11/11 15:53
 * @Version: 1.0
 */
@Api(value = "授权",tags = {"认证：认证授权接口"})
@RestController
public class SysLoginController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ShiroParam shiroParam;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * @Method login
     * @Author zhengxin
     * @Description 登录授权
     * @param loginDto 用户信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/20 14:45
     */
    @ApiOperation(value = "登录授权",httpMethod = "POST")
    @PostMapping(value = "/login")
    public HttpResult login(
            @ApiParam(name = "loginDto",value = "用户信息",required = true)
            @RequestBody LoginDto loginDto) throws Exception {
        // 查询数据库中的帐号信息
        SysUser user = userService.selectUserByUserName(loginDto.getUsername());
        if (user == null) {
            throw new ValidatorException("该帐号不存在(The account does not exist.)");
        }
        // 密码进行AES解密
        String key = AESUtils.Decrypt(loginDto.getPassword(),AESUtils.KEY);
        if (user.getPassword().equals(key)) {
            // 清除可能存在的Shiro权限信息缓存
            if (redisOperator.existKey(ConstantUtil.PREFIX_SHIRO_CACHE + loginDto.getUsername())) {
                redisOperator.del(ConstantUtil.PREFIX_SHIRO_CACHE + loginDto.getUsername());
            }

            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            // 生成token
            String token = JwtUtil.sign(loginDto.getUsername(), currentTimeMillis);
            Map<String,Object> map =new HashMap<>();
            //设置redis的token缓存
            redisOperator.set(ConstantUtil.PREFIX_SHIRO_REFRESH_TOKEN + loginDto.getUsername(), token, Integer.parseInt(shiroParam.getAccessTokenExpireTime()));
            //设置redis的用户缓存
            redisOperator.set(ConstantUtil.PREFIX_USER_CACHE+ loginDto.getUsername(), JsonUtils.objectToJson(user), Integer.parseInt(shiroParam.getAccessTokenExpireTime()));
            map.put("token",token);
            return HttpResult.ok(map);
        }
        else {
            return HttpResult.error("密码错误");
        }
    }


    /**
     * @Method getUserInfo
     * @Author zhengxin
     * @Description 获取用户信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/20 14:46
     */
    @ApiOperation(value = "获取用户信息",httpMethod = "GET")
    @GetMapping("/getInfo")
    public HttpResult getUserInfo(){
        Map<String,Object> map=new HashMap<>();
        //获取登录用户信息
        SysUser user=loginUserService.getLoginUser();
        // 角色集合
        Set<String> roles = roleService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = menuService.selectMenuPermsByUser(user);
        map.put("user",user);
        map.put("roles",roles);
        map.put("permissions",permissions);
        return HttpResult.ok(map);
    }

    /**
     * @Method getRouters
     * @Author zhengxin
     * @Description 获取路由信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/20 14:47
     */
    @ApiOperation(value = "获取路由",httpMethod = "GET")
    @GetMapping("getRouters")
    public HttpResult getRouters() {
        //获取登录用户信息
        SysUser user=loginUserService.getLoginUser();
        // 用户信息
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user);
        List<RouterVo> r=menuService.buildMenus(menus);
        return HttpResult.ok(menuService.buildMenus(menus));
    }


    /**
     * @Method logout
     * @Author zhengxin
     * @Description 登出
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 16:47
     */
    @ApiOperation(value = "登出",httpMethod = "POST")
    @PostMapping("/logout")
    public HttpResult logout(){
        //获取登录用户信息
        SysUser user=loginUserService.getLoginUser();
        // 清除可能存在的Shiro权限信息缓存
        if (redisOperator.existKey(ConstantUtil.PREFIX_SHIRO_CACHE + user.getUserName())) {
            redisOperator.del(ConstantUtil.PREFIX_SHIRO_CACHE + user.getUserName());
        }

        return HttpResult.ok();
    }

}
