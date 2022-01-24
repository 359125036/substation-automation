package com.substation.service.system;

import com.substation.entity.dto.SysUserDto;
import com.substation.entity.system.SysUser;

import java.util.List;

/**
 * @ClassName: ISysUserService
 * @Author: zhengxin
 * @Description: 用户 业务层
 * @Date: 2020/11/16 16:53
 * @Version: 1.0
 */
public interface ISysUserService {

    /**
     * @Method selectOne
     * @Author zhengxin
     * @Description 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param user 实体
     * @Return SysUser
     * @Date 2020/11/10 22:07
     */
    SysUser selectOne(SysUser user);

    /**
     * @Method selectUserByUserName
     * @Author zhengxin
     * @Description 通过用户名称获取用户信息
     * @param userName 实体
     * @Return SysUser
     * @Date 2020/11/20 16:07
     */
    public SysUser selectUserByUserName(String userName);
    /**
     * 根据条件分页查询用户列表
     *
     * @param userDto 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUserDto userDto);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);


    /**
     * 校验用户是否允许操作
     *
     * @param userDto 用户信息
     */
    public boolean checkUserAllowed(SysUserDto userDto);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public String checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(SysUserDto user);

    /**
     * 新增用户
     * @param userDto
     * @return
     */
    public int insertUser(SysUserDto userDto);

    /**
     * 修改用户
     * @param userDto
     * @return
     */
    public int updateUser(SysUserDto userDto);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);


    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserStatus(SysUser user);
}
