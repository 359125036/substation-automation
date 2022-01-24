package com.substation.service.system;


import com.substation.entity.dto.SysDeptDto;
import com.substation.entity.system.SysDept;
import com.substation.entity.vo.TreeSelect;

import java.util.List;

/**
 * @ClassName: ISysDeptService
 * @Author: zhengxin
 * @Description: 部门管理 业务层
 * @Date: 2020/11/16 17:03
 * @Version: 1.0
 */
public interface ISysDeptService {

    /**
     * 查询部门管理数据
     *
     * @param deptDto 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDeptDto deptDto);

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    public List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptDto 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDeptDto deptDto);


    /**
     * 新增保存部门信息
     *
     * @param deptDto 部门信息
     * @return 结果
     */
    public int insertDept(SysDeptDto deptDto);

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 查询部门是否存在设备
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean checkDeptExistEquip(Long deptId);


    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);


    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);


    /**
     * 修改保存部门信息
     *
     * @param deptDto 部门信息
     * @return 结果
     */
    public int updateDept(SysDeptDto deptDto);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);
}
