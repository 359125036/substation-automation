package com.substation.service.system;

import com.substation.entity.dto.SysRoleDto;
import com.substation.entity.system.SysRole;
import com.substation.entity.system.SysUser;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: ISysRoleService
 * @Author: zhengxin
 * @Description: 权限业务层
 * @Date: 2020/11/16 17:02
 * @Version: 1.0
 */
public interface ISysRoleService {

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user);


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    //获取所有角色
    public List<SysRole> selectAllRole();


    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Integer> selectRoleListByUserId(Long userId);


    /**
     * 根据条件分页查询角色数据
     *
     * @param roleDto 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRoleDto roleDto);


    /**
     * 校验角色名称是否唯一
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    public String checkRoleNameUnique(SysRoleDto roleDto);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    public String checkRoleKeyUnique(SysRoleDto roleDto);

    /**
     * 新增保存角色信息
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    public int insertRole(SysRoleDto roleDto);


    /**
     * 修改保存角色信息
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    public int updateRole(SysRoleDto roleDto);


    /**
     * 修改角色状态
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    public int updateRoleStatus(SysRoleDto roleDto);


    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds);


    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);


    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(Long roleId);

}
