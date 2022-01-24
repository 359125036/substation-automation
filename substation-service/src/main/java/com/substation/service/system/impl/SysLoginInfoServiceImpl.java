package com.substation.service.system.impl;

import com.substation.entity.dto.SysLoginInfoDto;
import com.substation.entity.system.SysLoginInfo;
import com.substation.mapper.SysLoginInfoMapper;
import com.substation.service.system.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName:    SysLoginInfoServiceImpl
 * Package:    com.yddl.service.system.impl
 * Description: 登录访问记录业务层
 * Datetime:    2020/11/25   15:02
 * Author:   zx
 */
@Service
public class SysLoginInfoServiceImpl implements ISysLoginInfoService {

    @Autowired
    private SysLoginInfoMapper loginInfoMapper;

    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insertLoginInfo(SysLoginInfo loginInfo) {
        loginInfoMapper.insert(loginInfo);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfoDto 访问日志对象
     * @return 登录记录集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysLoginInfo> selectLoginInfoList(SysLoginInfoDto loginInfoDto) {
        return loginInfoMapper.selectLoginInfoList(loginInfoDto);
    }


    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        return loginInfoMapper.deleteLoginInfoByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginInfo() {
        loginInfoMapper.cleanLoginInfo();
    }
}
