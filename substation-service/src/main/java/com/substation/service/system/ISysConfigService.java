package com.substation.service.system;

import com.substation.entity.dto.SysConfigDto;
import com.substation.entity.system.SysConfig;

import java.util.List;

/**
 * ClassName:    ISysConfig
 * Package:    com.yddl.service.system
 * Description: 系统配置业务
 * Datetime:    2020/11/25   9:10
 * Author:   zx
 */
public interface ISysConfigService {

    /**
     * 查询参数配置列表
     *
     * @param configDto 参数配置信息
     * @return 参数配置集合
     */
    public List<SysConfig> selectConfigList(SysConfigDto configDto);


    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    public SysConfig selectConfigById(Integer configId);

    /**
     * 校验参数键名是否唯一
     *
     * @param configDto 参数信息
     * @return 结果
     */
    public String checkConfigKeyUnique(SysConfigDto configDto);


    /**
     * 新增参数配置
     *
     * @param configDto 参数配置信息
     * @return 结果
     */
    public int insertConfig(SysConfigDto configDto);


    /**
     * 修改参数配置
     *
     * @param configDto 参数配置信息
     * @return 结果
     */
    public int updateConfig(SysConfigDto configDto);


    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    public int deleteConfigByIds(Long[] configIds);



    /**
     * 清空缓存数据
     */
    public void clearCache();


    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);

}
