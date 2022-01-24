package com.substation.service.system.impl;

import com.substation.constant.UserConstants;
import com.substation.entity.dto.SysDeptDto;
import com.substation.entity.system.SysDept;
import com.substation.entity.system.SysEquip;
import com.substation.entity.vo.TreeSelect;
import com.substation.enums.DelFlagEnum;
import com.substation.exception.ValidatorException;
import com.substation.mapper.SysDeptMapper;
import com.substation.mapper.SysEquipMapper;
import com.substation.service.system.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: SysDeptServiceImpl
 * @Author: zhengxin
 * @Description: 部门 业务层处理
 * @Date: 2020/11/16 17:05
 * @Version: 1.0
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysEquipMapper equipMapper;

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectByPrimaryKey(deptId);
    }

    /**
     * 新增保存部门信息
     *
     * @param deptDto 部门信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertDept(SysDeptDto deptDto) {
        //获取父部门
        SysDept sysDept = deptMapper.selectByPrimaryKey(deptDto.getParentId());
        SysDept dept=new SysDept();
        BeanUtils.copyProperties(deptDto,dept);
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(sysDept.getStatus()))
            throw new ValidatorException("部门停用，不允许新增");
        dept.setAncestors(sysDept.getAncestors() + "," + dept.getParentId());
        dept.setDelFlag(DelFlagEnum.EXIST_FLAG_ENUM.type);
        dept.setCreateTime(new Date());
        return deptMapper.insert(dept);
    }


    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        SysDept dept=new SysDept();
        dept.setParentId(deptId);
        dept.setDelFlag(DelFlagEnum.EXIST_FLAG_ENUM.type);
        List<SysDept> select = deptMapper.select(dept);
        return select.size()>0 ? true : false;
    }
    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在设备
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean checkDeptExistEquip(Long deptId) {
        Example example=new Example(SysEquip.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deptId",deptId);
        List<SysEquip> sysEquips = equipMapper.selectByExample(example);
        return sysEquips.size()>0 ? true :false;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Transactional(propagation =Propagation.REQUIRED)
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteByPrimaryKey(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Transactional(propagation =Propagation.SUPPORTS)
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 修改保存部门信息
     *
     * @param deptDto 部门信息
     * @return 结果
     */
    @Override
    @Transactional(propagation =Propagation.REQUIRED)
    public int updateDept(SysDeptDto deptDto) {
        //获取新的上级部门
        SysDept newParentDept = deptMapper.selectByPrimaryKey(deptDto.getParentId());
        //获取旧部门
        SysDept oldDept = deptMapper.selectByPrimaryKey(deptDto.getDeptId());
        if (null!=newParentDept && null!=oldDept) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            deptDto.setAncestors(newAncestors);
            updateDeptChildren(deptDto.getDeptId(), newAncestors, oldAncestors);
        }
        SysDept dept=new SysDept();
        BeanUtils.copyProperties(deptDto,dept);
        int result = deptMapper.updateByPrimaryKeySelective(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }


    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectByPrimaryKey(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }


    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param deptDto 部门信息
     * @return 结果
     */
    @Transactional(propagation =Propagation.SUPPORTS)
    @Override
    public String checkDeptNameUnique(SysDeptDto deptDto) {
        Long deptId=deptDto.getDeptId()==null?-1L:deptDto.getDeptId();
        Example example=new Example(SysDept.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deptId",deptId);
        criteria.andEqualTo("parentId",deptDto.getParentId());
        SysDept sysDept = deptMapper.selectOneByExample(example);
        if (sysDept!=null && sysDept.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 查询部门管理数据
     *
     * @param deptDto 部门信息
     * @return 部门信息集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysDept> selectDeptList(SysDeptDto deptDto) {
        SysDept dept=new SysDept();
        BeanUtils.copyProperties(deptDto,dept);
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();)
        {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysDept> it = childList.iterator();
                while (it.hasNext())
                {
                    SysDept n = (SysDept) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (null!=n.getParentId() && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }


    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
