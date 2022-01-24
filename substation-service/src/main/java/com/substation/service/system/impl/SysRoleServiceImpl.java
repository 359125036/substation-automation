package com.substation.service.system.impl;

import com.substation.constant.UserConstants;
import com.substation.entity.dto.SysRoleDto;
import com.substation.entity.system.SysRole;
import com.substation.entity.system.SysRoleMenu;
import com.substation.entity.system.SysUser;
import com.substation.enums.DelFlagEnum;
import com.substation.exception.ValidatorException;
import com.substation.mapper.SysRoleMapper;
import com.substation.mapper.SysRoleMenuMapper;
import com.substation.mapper.SysUserRoleMapper;
import com.substation.service.system.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @ClassName: SysRoleServiceImpl
 * @Author: zhengxin
 * @Description: 权限 业务层处理
 * @Date: 2020/11/16 17:15
 * @Version: 1.0
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {


    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;
    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        }else{
            roles=selectRolePermissionByUserId(user.getUserId());
        }
        return roles;
    }

    //根据用户id查询角色权限
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        Set<String> roles = new HashSet<>();
        List<SysRole> roleList = roleMapper.selectRolePermissionByUserId(userId);
        for (SysRole role : roleList)
        {
            if (role!=null)
            {
                roles.addAll(Arrays.asList(role.getRoleKey().trim().split(",")));
            }
        }
       return roles;
    }

    //获取所有角色
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysRole> selectAllRole() {
        return roleMapper.selectAll();
    }


    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Integer> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }



    /**
     * 根据条件分页查询角色数据
     *
     * @param roleDto 角色信息
     * @return 角色数据集合信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysRole> selectRoleList(SysRoleDto roleDto) {
        Example example=new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        //未删除
        criteria.andEqualTo("delFlag", DelFlagEnum.EXIST_FLAG_ENUM.type);
        if(StringUtils.isNotBlank(roleDto.getRoleName()))
            criteria.andLike("roleName","%"+roleDto.getRoleName()+"%");
        criteria.andEqualTo("status",roleDto.getStatus());
        if(StringUtils.isNotBlank(roleDto.getRoleKey()))
            criteria.andLike("roleKey","%"+roleDto.getRoleKey()+"%");
        return roleMapper.selectByExample(example);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String checkRoleNameUnique(SysRoleDto roleDto) {
        Long roleId = roleDto.getRoleId()==null ? -1L : roleDto.getRoleId();
        Example example=new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName",roleDto.getRoleName());
        SysRole info = roleMapper.selectOneByExample(example);
        if (null!=info && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String checkRoleKeyUnique(SysRoleDto roleDto) {
        Long roleId = roleDto.getRoleId()==null ? -1L : roleDto.getRoleId();
        Example example=new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleKey",roleDto.getRoleKey());
        SysRole info = roleMapper.selectOneByExample(example);
        if (null!=info && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存角色信息
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertRole(SysRoleDto roleDto) {
        SysRole role=new SysRole();
        BeanUtils.copyProperties(roleDto,role);
        role.setCreateTime(new Date());
        role.setDelFlag(DelFlagEnum.EXIST_FLAG_ENUM.type);
        // 新增角色信息
        roleMapper.insert(role);
        roleDto.setRoleId(role.getRoleId());
        //新增角色菜单关联
        return insertRoleMenu(roleDto);
    }


    /**
     * 新增角色菜单信息
     *
     * @param roleDto 角色对象
     */
    public int insertRoleMenu(SysRoleDto roleDto)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : roleDto.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(roleDto.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 修改保存角色信息
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateRole(SysRoleDto roleDto) {
        SysRole role=new SysRole();
        BeanUtils.copyProperties(roleDto,role);
        role.setUpdateTime(new Date());
        // 修改角色信息
        roleMapper.updateByPrimaryKeySelective(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(roleDto);
    }

    /**
     * 修改角色状态
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateRoleStatus(SysRoleDto roleDto) {
        SysRole role=new SysRole();
        BeanUtils.copyProperties(roleDto,role);
        role.setUpdateTime(new Date());
        // 修改角色信息
        return roleMapper.updateByPrimaryKeySelective(role);
    }


    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            if (null!=roleId && roleId==1l)
            {
                throw new ValidatorException("不允许操作超级管理员角色");
            }
            SysRole role = roleMapper.selectByPrimaryKey(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new ValidatorException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }


    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysRole selectRoleById(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }
}
