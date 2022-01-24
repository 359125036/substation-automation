package com.substation.mapper;

import com.substation.entity.dto.SysConfigDto;
import com.substation.entity.system.SysConfig;
import com.substation.my.mapper.MyMapper;

import java.util.List;

public interface SysConfigMapper extends MyMapper<SysConfig> {

    /**
     * 查询参数配置列表
     *
     * @param configDto 参数配置信息
     * @return 参数配置集合
     */
    public List<SysConfig> selectConfigList(SysConfigDto configDto);

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    public int deleteConfigByIds(Long[] configIds);
}