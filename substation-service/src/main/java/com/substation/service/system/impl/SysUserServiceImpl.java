package com.substation.service.system.impl;

import com.substation.constant.UserConstants;
import com.substation.entity.dto.SysUserDto;
import com.substation.entity.system.SysUser;
import com.substation.entity.system.SysUserPost;
import com.substation.entity.system.SysUserRole;
import com.substation.enums.DelFlagEnum;
import com.substation.mapper.SysUserMapper;
import com.substation.mapper.SysUserPostMapper;
import com.substation.mapper.SysUserRoleMapper;
import com.substation.service.system.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: SysUserServiceImpl
 * @Author: zhengxin
 * @Description: 用户 业务层处理
 * @Date: 2020/11/16 16:55
 * @Version: 1.0
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;
    /**
     * @Method selectOne
     * @Author zhengxin
     * @Description 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param user 实体
     * @Return SysUser
     * @Date 2020/11/10 22:07
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysUser selectOne(SysUser user) {
        return userMapper.selectOne(user);
    }

    /**
     * @Method selectUserByUserName
     * @Author zhengxin
     * @Description 通过用户名称获取用户信息
     * @param userName 用户名称
     * @Return SysUser
     * @Date 2020/11/20 16:07
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysUser selectUserByUserName(String userName) {
        Example example=new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName",userName);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param userDto 用户信息
     * @return 用户信息集合信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysUser> selectUserList(SysUserDto userDto) {
        return userMapper.selectUserList(userDto);
    }


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 校验用户是否允许操作
     *
     * @param userDto 用户信息
     */
    @Override
    public boolean checkUserAllowed(SysUserDto userDto) {
        SysUser user=new SysUser();
        BeanUtils.copyProperties(userDto,user);
        if (null!=user.getUserId() && user.isAdmin())
        {
           return false;
        }
        return true;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        Example example=new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName",userName);
        List<SysUser> sysUsers = userMapper.selectByExample(example);
        if(sysUsers!=null&&sysUsers.size()>0)
            return UserConstants.NOT_UNIQUE;
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public String checkPhoneUnique(SysUserDto user) {
        Example example=new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phoneNumber",user.getPhoneNumber());
        List<SysUser> sysUsers = userMapper.selectByExample(example);
        if(sysUsers!=null&&sysUsers.size()>0)
            return UserConstants.NOT_UNIQUE;
        return UserConstants.UNIQUE;
    }

    /**
     * 新增用户
     * @param userDto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertUser(SysUserDto userDto) {
        SysUser user=new SysUser();
        BeanUtils.copyProperties(userDto,user);
        user.setDelFlag(DelFlagEnum.EXIST_FLAG_ENUM.type);
        user.setCreateTime(new Date());
        //新增用户
        int insert = userMapper.insert(user);
        userDto.setUserId(user.getUserId());
        // 新增用户岗位关联
        insertUserPost(userDto);
        // 新增用户与角色管理
        insertUserRole(userDto);
        return insert;
    }


    /**
     * 新增用户岗位信息
     *
     * @param userDto 用户对象
     */
    public void insertUserPost(SysUserDto userDto) {
        Long[] posts = userDto.getPostIds();
        if (posts!=null)
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(userDto.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                //批量添加用户与部门关联
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userDto 用户对象
     */
    public void insertUserRole(SysUserDto userDto)
    {
        Long[] roles = userDto.getRoleIds();
        if (roles!=null)
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userDto.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 修改用户
     * @param userDto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateUser(SysUserDto userDto) {
        SysUser user=new SysUser();
        BeanUtils.copyProperties(userDto,user);
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(user.getUserId());
        // 新增用户与角色管理
        insertUserRole(userDto);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(user.getUserId());
        // 新增用户与岗位管理
        insertUserPost(userDto);
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteUserByIds(Long[] userIds) {

        //TODO 可以把sys_user_role关联也删除（虚拟删除暂时不用删除关联）
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
