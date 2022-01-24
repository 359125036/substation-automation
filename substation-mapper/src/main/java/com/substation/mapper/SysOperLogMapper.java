package com.substation.mapper;

import com.substation.entity.dto.SysOperLogDto;
import com.substation.entity.system.SysOperLog;
import com.substation.my.mapper.MyMapper;

import java.util.List;

public interface SysOperLogMapper extends MyMapper<SysOperLog> {


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