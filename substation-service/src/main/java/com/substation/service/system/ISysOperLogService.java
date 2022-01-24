package com.substation.service.system;

import com.substation.entity.dto.SysOperLogDto;
import com.substation.entity.system.SysOperLog;

import java.util.List;

/**
 * ClassName:    ISysOperLogService
 * Package:    com.yddl.service.system
 * Description: 操作日志业务
 * Datetime:    2020/11/25   10:24
 * Author:   zx
 */
public interface ISysOperLogService {

    /**
     * 查询系统操作日志集合
     *
     * @param operLogDto 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperLog> selectOperLogList(SysOperLogDto operLogDto);


    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(String[] operIds);


    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}
