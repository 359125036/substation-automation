package com.substation.controller.system;


import com.substation.constant.UserConstants;
import com.substation.controller.BaseController;
import com.substation.entity.dto.SysUserDto;
import com.substation.entity.system.SysRole;
import com.substation.entity.system.SysUser;
import com.substation.http.HttpResult;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysPostService;
import com.substation.service.system.ISysRoleService;
import com.substation.service.system.ISysUserService;
import com.substation.utils.AESUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: SysUserController
 * @Author: zhengxin
 * @Description: 用户信息
 * @Date: 2020/11/11 15:53
 * @Version: 1.0
 */
@Api(value = "用户信息",tags = {"系统：系统用户接口"})
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    public static final String BUSINESS_NAME = "用户管理";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private LoginUserService loginUserService;


    /**
     * @Method queryUserList
     * @Author zhengxin
     * @Description 查询用户列表
     * @param userDto 用户信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:45
     */
    @ApiOperation(value = "查询用户列表",httpMethod = "GET")
    @GetMapping("/queryUserList")
    public HttpResult queryUserList(
            @ApiParam(name = "userDto",value = "用户信息",required = true)
                    SysUserDto userDto){
        startPage();
        List<SysUser> list = userService.selectUserList(userDto);
        return HttpResult.ok(getDataTable(list));
    }


    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 根据用户编号获取详细信息
     * @param userId 用户ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:55
     */
    @ApiOperation(value = "根据用户编号获取详细信息",httpMethod = "GET")
    @GetMapping(value = { "/", "/{userId}" })
    public HttpResult getInfo(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @PathVariable(value = "userId", required = false) Long userId) {
        Map<String,Object> map=new HashMap<>();
        List<SysRole> roles = roleService.selectAllRole();
        map.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        map.put("posts", postService.selectAllPost());
        if (userId!=null)
        {
            map.put(UserConstants.DATA_TAG, userService.selectUserById(userId));
            map.put("postIds", postService.selectPostListByUserId(userId));
            map.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return HttpResult.ok(map);
    }

    /**
     * @Method saveUser
     * @Author zhengxin
     * @Description 新增用户
     * @param userDto 用户
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/23 14:55
     */
    @ApiOperation(value = "新增用户",httpMethod = "POST")
    @PostMapping("/addUser")
    public HttpResult saveUser(
            @ApiParam(name = "userDto",value = "用户信息",required = true)
            @RequestBody SysUserDto userDto){
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(userDto.getUserName())))
        {
            return HttpResult.error("新增用户'" + userDto.getUserName() + "'失败，登录账号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(userDto)))
        {
            return HttpResult.error("新增用户'" + userDto.getUserName() + "'失败，手机号码已存在");
        }
        String pwd=null;
        userDto.setCreateBy(loginUserService.getUsername());
        try {
             pwd = AESUtils.Decrypt(userDto.getPassword(), AESUtils.KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDto.setPassword(pwd);
        return toAjax(userService.insertUser(userDto));
    }

    /**
     * @Method updateUser
     * @Author zhengxin
     * @Description 更新用户
     * @param userDto 用户
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/23 15:20
     */
    @ApiOperation(value = "更新用户",httpMethod = "PUT")
    @PutMapping("/updateUser")
    public HttpResult updateUser(
            @ApiParam(name = "userDto",value = "用户",required = true)
            @RequestBody SysUserDto userDto){
        //验证是否修改超级管理员
        boolean isAllowed=userService.checkUserAllowed(userDto);
        if(!isAllowed){
            return HttpResult.error("不允许操作超级管理员用户");
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(userDto)))
            return HttpResult.error("修改用户'" + userDto.getUserName() + "'失败，手机号码已存在");

        userDto.setUpdateBy(loginUserService.getUsername());
        return toAjax(userService.updateUser(userDto));
    }

    /**
     * @Method deleteUser
     * @Author zhengxin
     * @Description 更新用户
     * @param userIds 用户ID数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/23 15:22
     */
    @ApiOperation(value = "删除用户",httpMethod = "DELETE")
    @DeleteMapping("deleteUser/{userIds}")
    public HttpResult deleteUser(
            @ApiParam(name = "userIds",value = "用户ID数组",required = true)
            @PathVariable(value = "userIds", required = true) Long[] userIds){
        //避免超级管理员被删除
        for (Long userId : userIds) {
            boolean isAllowed = userService.checkUserAllowed(new SysUserDto(userId));
            if(!isAllowed)
                return HttpResult.error("不允许操作超级管理员用户");
        }
        return HttpResult.ok(toAjax(userService.deleteUserByIds(userIds)));
    }

    /**
     * @Method changeStatus
     * @Author zhengxin
     * @Description 修改用户状态
     * @param user 用户
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/23 15:24
     */
    @ApiOperation(value = "修改用户状态",httpMethod = "PUT")
    @PutMapping("/changeStatus")
    public HttpResult changeStatus(
            @ApiParam(name = "user",value = "用户",required = true)
            @RequestBody SysUser user)
    {
        if (null!=user.getUserId() && user.isAdmin())
            HttpResult.error("不允许操作超级管理员角色");
        user.setUpdateBy(loginUserService.getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

}
