package com.substation.controller.system;


import com.substation.constant.UserConstants;
import com.substation.controller.BaseController;
import com.substation.entity.dto.SysRoleDto;
import com.substation.entity.system.SysRole;
import com.substation.http.HttpResult;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SysRoleController
 * @Author: zhengxin
 * @Description: 角色信息
 * @Date: 2020/11/19 10:53
 * @Version: 1.0
 */
@Api(value = "角色信息",tags = {"系统：系统角色接口"})
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    public static final String BUSINESS_NAME = "角色管理";

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private LoginUserService loginUserService;


    /**
     * @Method queryRoleList
     * @Author zhengxin
     * @Description 查询角色列表
     * @param roleDto 角色信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:02
     */
    @ApiOperation(value = "查询角色列表",httpMethod = "GET")
    @GetMapping("/queryRoleList")
    public HttpResult queryRoleList(
            @ApiParam(name = "roleDto",value = "角色信息")
                    SysRoleDto roleDto){
        startPage();
        List<SysRole> list = roleService.selectRoleList(roleDto);
        return HttpResult.ok(getDataTable(list));
    }

    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 根据角色编号获取详细信息
     * @param roleId 角色ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:05
     */
    @ApiOperation(value = "根据角色编号获取详细信息",httpMethod = "GET")
    @GetMapping(value = "/{roleId}")
    public HttpResult getInfo(
            @ApiParam(name = "roleId",value = "角色ID")
            @PathVariable Long roleId) {
        return HttpResult.ok(roleService.selectRoleById(roleId));
    }

    /**
     * @Method saveRole
     * @Author zhengxin
     * @Description 新增角色
     * @param roleDto 角色信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:08
     */
    @ApiOperation(value = "新增角色",httpMethod = "POST")
    @PostMapping("/saveRole")
    public HttpResult saveRole(
            @ApiParam(name = "roleDto",value = "角色信息",required = true)
            @RequestBody SysRoleDto roleDto) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(roleDto)))
        {
            return HttpResult.error("新增角色'" + roleDto.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(roleDto)))
        {
            return HttpResult.error("新增角色'" + roleDto.getRoleName() + "'失败，角色权限已存在");
        }
        roleDto.setCreateBy(loginUserService.getUsername());
        return toAjax(roleService.insertRole(roleDto));

    }


    /**
     * @Method updateRole
     * @Author zhengxin
     * @Description 修改保存角色
     * @param roleDto 角色信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:10
     */
    @ApiOperation(value = "修改角色",httpMethod = "PUT")
    @PutMapping("/updateRole")
    public HttpResult updateRole(
            @ApiParam(name = "roleDto",value = "角色信息",required = true)
            @RequestBody SysRoleDto roleDto) {
        if (roleDto.getRoleId()!=null && roleDto.isAdmin())
            HttpResult.error("不允许操作超级管理员角色");
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(roleDto)))
        {
            return HttpResult.error("修改角色'" + roleDto.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(roleDto)))
        {
            return HttpResult.error("修改角色'" + roleDto.getRoleName() + "'失败，角色权限已存在");
        }
        roleDto.setUpdateBy(loginUserService.getUsername());
        return toAjax(roleService.updateRole(roleDto));
    }


    /**
     * @Method changeStatus
     * @Author zhengxin
     * @Description 状态修改
     * @param roleDto 角色信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:12
     */
    @ApiOperation(value = "状态修改",httpMethod = "PUT")
    @PutMapping("/changeStatus")
    public HttpResult changeStatus(
            @ApiParam(name = "roleDto",value = "角色信息",required = true)
            @RequestBody SysRoleDto roleDto)
    {
        if (roleDto.getRoleId()!=null && roleDto.isAdmin())
            HttpResult.error("不允许操作超级管理员角色");
        roleDto.setUpdateBy(loginUserService.getUsername());
        return toAjax(roleService.updateRoleStatus(roleDto));
    }

    /**
     * @Method remove
     * @Author zhengxin
     * @Description 删除角色
     * @param roleIds 角色ID数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:16
     */
    @ApiOperation(value = "删除角色",httpMethod = "DELETE")
    @DeleteMapping("/deleteRole/{roleIds}")
    public HttpResult remove(
            @ApiParam(name = "roleIds",value = "角色ID数组",required = true)
            @PathVariable Long[] roleIds) {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }

}
