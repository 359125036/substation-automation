package com.substation.controller.system;

import com.substation.constant.Constants;
import com.substation.constant.UserConstants;
import com.substation.controller.BaseController;
import com.substation.entity.dto.SysMenuDto;
import com.substation.entity.system.SysMenu;
import com.substation.entity.system.SysUser;
import com.substation.http.HttpResult;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysMenuController
 * @Author: zhengxin
 * @Description: 菜单信息
 * @Date: 2020/11/11 15:53
 * @Version: 1.0
 */
@Api(value = "菜单信息",tags = {"系统：系统菜单接口"})
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    public static final String BUSINESS_NAME = "菜单管理";

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * @Method menuList
     * @Author zhengxin
     * @Description 获取菜单列表
     * @param menuDto 菜单信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:30
     */
    @ApiOperation(value = "菜单列表",httpMethod = "GET")
    @GetMapping("/menuList")
    public HttpResult menuList(
            @ApiParam(name = "menu",value = "菜单信息")
                    SysMenuDto menuDto) {
        //获取登录用户信息
        SysUser user=loginUserService.getLoginUser();
        Long userId = user.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menuDto, userId);
        return HttpResult.ok(menus);
    }

    /**
     * @Method treeSelect
     * @Author zhengxin
     * @Description 获取菜单下拉树列表
     * @param menuDto 菜单信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:32
     */
    @ApiOperation(value = "获取菜单下拉树列表",httpMethod = "GET")
    @GetMapping("/treeSelect")
    public HttpResult treeSelect(
            @ApiParam(name = "menu",value = "菜单信息")
            SysMenuDto menuDto)
    {
        //获取登录用户信息
        SysUser user=loginUserService.getLoginUser();
        List<SysMenu> menus = menuService.selectMenuList(menuDto, user.getUserId());
        return HttpResult.ok(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 根据菜单编号获取详细信息
     * @param menuId 菜单ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:34
     */
    @ApiOperation(value = "根据菜单编号获取详细信息",httpMethod = "GET")
    @GetMapping(value = "/{menuId}")
    public HttpResult getInfo(
            @ApiParam(name = "menuId",value = "菜单ID")
            @PathVariable Long menuId)
    {
        return HttpResult.ok(menuService.selectMenuById(menuId));
    }


    /**
     * @Method saveMenu
     * @Author zhengxin
     * @Description 新增菜单
     * @param menuDto 菜单
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:36
     */
    @ApiOperation(value = "新增菜单",httpMethod = "POST")
    @PostMapping("/saveMenu")
    public HttpResult saveMenu(
            @ApiParam(name = "menu",value = "菜单")
            @RequestBody SysMenuDto menuDto) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menuDto)))
        {
            return HttpResult.error("新增菜单'" + menuDto.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menuDto.getIsFrame())
                && !StringUtils.startsWithAny(menuDto.getPath(), Constants.HTTP, Constants.HTTPS))
        {
            return HttpResult.error("新增菜单'" + menuDto.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        //设置操作用户
        menuDto.setCreateBy(loginUserService.getUsername());
        return toAjax(menuService.insertMenu(menuDto));
    }

    /**
     * @Method updateMenu
     * @Author zhengxin
     * @Description 修改菜单
     * @param menuDto 菜单
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:38
     */
    @ApiOperation(value = "修改菜单",httpMethod = "PUT")
    @PutMapping("/updateMenu")
    public HttpResult updateMenu(
            @ApiParam(name = "menu",value = "菜单")
            @RequestBody SysMenuDto menuDto)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menuDto)))
        {
            return HttpResult.error("修改菜单'" + menuDto.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menuDto.getIsFrame())
                && !StringUtils.startsWithAny(menuDto.getPath(), Constants.HTTP, Constants.HTTPS))
        {
            return HttpResult.error("新增菜单'" + menuDto.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        //设置操作用户
        menuDto.setUpdateBy(loginUserService.getUsername());
        return toAjax(menuService.updateMenu(menuDto));
    }


    /**
     * @Method deleteMenu
     * @Author zhengxin
     * @Description 删除菜单
     * @param menuId 菜单ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:40
     */
    @ApiOperation(value = "删除菜单",httpMethod = "DELETE")
    @DeleteMapping("/deleteMenu/{menuId}")
    public HttpResult remove(
            @ApiParam(name = "menuId",value = "菜单ID",required = true)
            @PathVariable("menuId") Long menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return HttpResult.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return HttpResult.error("菜单已分配,不允许删除");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }


    /**
     * @Method roleMenuTreeselect
     * @Author zhengxin
     * @Description 加载对应角色菜单列表树
     * @param roleId 角色ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:40
     */
    @ApiOperation(value = "加载对应角色菜单列表树",httpMethod = "GET")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public HttpResult roleMenuTreeselect(
            @ApiParam(name = "roleId",value = "角色ID",required = true)
            @PathVariable("roleId") Long roleId)
    {
        //获取登录用户信息
        SysUser user=loginUserService.getLoginUser();
        List<SysMenu> menus = menuService.selectMenuList(user.getUserId());
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        map.put("menus", menuService.buildMenuTreeSelect(menus));
        return HttpResult.ok(map);
    }

}
