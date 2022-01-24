package com.substation.service.system;

import com.substation.entity.dto.SysMenuDto;
import com.substation.entity.system.SysMenu;
import com.substation.entity.system.SysUser;
import com.substation.entity.vo.RouterVo;
import com.substation.entity.vo.TreeSelect;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: ISysMenuService
 * @Author: zhengxin
 * @Description: 菜单 业务层
 * @Date: 2020/11/16 17:03
 * @Version: 1.0
 */
public interface ISysMenuService {

    /**
     * 根据用户查询权限
     *
     * @param user 用户
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUser(SysUser user);


    /**
     * 根据用户ID查询菜单树信息
     *
     * @param user 用户
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(SysUser user);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);


    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menuDto 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenuDto menuDto, Long userId);


    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);


    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public SysMenu selectMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuDto 菜单信息
     * @return 结果
     */
    public String checkMenuNameUnique(SysMenuDto menuDto);

    /**
     * 新增保存菜单信息
     *
     * @param menuDto 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenuDto menuDto);


    /**
     * 修改保存菜单信息
     *
     * @param menuDto 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenuDto menuDto);


    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByMenuId(Long menuId);


    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkMenuExistRole(Long menuId);


    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(Long menuId);


    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    public List<Integer> selectMenuListByRoleId(Long roleId);
}
