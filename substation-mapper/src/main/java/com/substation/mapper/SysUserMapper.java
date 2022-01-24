package com.substation.mapper;

import com.substation.entity.dto.SysUserDto;
import com.substation.my.mapper.MyMapper;
import com.substation.entity.system.SysUser;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {

    /**
     * 根据条件分页查询用户列表
     *
     * @param userDto 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUserDto userDto);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);
}