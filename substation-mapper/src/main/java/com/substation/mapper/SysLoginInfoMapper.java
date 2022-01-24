package com.substation.mapper;

import com.substation.entity.dto.SysLoginInfoDto;
import com.substation.entity.system.SysLoginInfo;
import com.substation.my.mapper.MyMapper;

import java.util.List;

public interface SysLoginInfoMapper extends MyMapper<SysLoginInfo> {

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfoDto 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLoginInfo> selectLoginInfoList(SysLoginInfoDto loginInfoDto);


    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    public int deleteLoginInfoByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    public int cleanLoginInfo();
}