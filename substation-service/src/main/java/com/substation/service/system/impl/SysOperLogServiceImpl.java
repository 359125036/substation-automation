package com.substation.service.system.impl;

import com.substation.entity.dto.SysOperLogDto;
import com.substation.entity.system.SysOperLog;
import com.substation.mapper.SysOperLogMapper;
import com.substation.service.system.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName:    SysOperLogServiceImpl
 * Package:    com.yddl.service.system.impl
 * Description: 操作日志业务
 * Datetime:    2020/11/25   10:24
 * Author:   zx
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {


    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 查询系统操作日志集合
     *
     * @param operLogDto 操作日志对象
     * @return 操作日志集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLogDto operLogDto) {
        return operLogMapper.selectOperLogList(operLogDto);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteOperLogByIds(String[] operIds)
    {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog()
    {
        operLogMapper.cleanOperLog();
    }
}
