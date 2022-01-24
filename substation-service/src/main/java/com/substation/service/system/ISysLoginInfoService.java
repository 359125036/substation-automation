package com.substation.service.system;

import com.substation.entity.dto.SysLoginInfoDto;
import com.substation.entity.system.SysLoginInfo;

import java.util.List;

/**
 * ClassName:    ISysLoginInfoService
 * Package:    com.yddl.service.system
 * Description: 登录访问记录业务层
 * Datetime:    2020/11/25   15:02
 * Author:   zx
 */
public interface ISysLoginInfoService {


    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    public void insertLoginInfo(SysLoginInfo loginInfo);


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
     * @return
     */
    public int deleteLoginInfoByIds(Long[] infoIds);


    /**
     * 清空系统登录日志
     */
    public void cleanLoginInfo();
}
