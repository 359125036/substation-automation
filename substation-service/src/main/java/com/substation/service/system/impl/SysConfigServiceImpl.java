package com.substation.service.system.impl;

import com.substation.constant.Constants;
import com.substation.constant.Convert;
import com.substation.constant.UserConstants;
import com.substation.entity.dto.SysConfigDto;
import com.substation.entity.system.SysConfig;
import com.substation.mapper.SysConfigMapper;
import com.substation.service.system.ISysConfigService;
import com.substation.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * ClassName:    SysConfigImpl
 * Package:    com.yddl.service.system.impl
 * Description: 系统配置业务
 * Datetime:    2020/11/25   9:11
 * Author:   zx
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {


    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 查询参数配置列表
     *
     * @param configDto 参数配置信息
     * @return 参数配置集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysConfig> selectConfigList(SysConfigDto configDto) {
        return configMapper.selectConfigList(configDto);
    }

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysConfig selectConfigById(Integer configId) {
        return configMapper.selectByPrimaryKey(configId);
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param configDto 参数信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String checkConfigKeyUnique(SysConfigDto configDto) {
        Long configId = configDto.getConfigId()==null ? -1L : configDto.getConfigId();
        Example example=new Example(SysConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configKey",configDto.getConfigKey());
        SysConfig info = configMapper.selectOneByExample(example);
        if (info!=null && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 新增参数配置
     *
     * @param configDto 参数配置信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertConfig(SysConfigDto configDto) {
        SysConfig config=new SysConfig();
        BeanUtils.copyProperties(configDto,config);
        config.setCreateTime(new Date());
        //新增
        int row = configMapper.insert(config);
        //设置配置缓存
        if (row > 0) {
            redisOperator.set(Constants.SYS_CONFIG_KEY+config.getConfigKey(), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     *
     * @param configDto 参数配置信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateConfig(SysConfigDto configDto) {
        SysConfig config=new SysConfig();
        BeanUtils.copyProperties(configDto,config);
        config.setUpdateTime(new Date());
        int row = configMapper.updateByPrimaryKeySelective(config);
        //更新缓存
        if (row > 0) {
            redisOperator.set(Constants.SYS_CONFIG_KEY+config.getConfigKey(), config.getConfigValue());
        }
        return row;
    }


    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteConfigByIds(Long[] configIds)
    {
        int count = configMapper.deleteConfigByIds(configIds);
        if (count > 0) {
            Collection<String> keys = redisOperator.keys(Constants.SYS_CONFIG_KEY + "*");
            redisOperator.deleteObject(keys);
        }
        return count;
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache() {
        Collection<String> keys = redisOperator.keys(Constants.SYS_CONFIG_KEY + "*");
        redisOperator.deleteObject(keys);
    }


    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(redisOperator.get(Constants.SYS_CONFIG_KEY +configKey));
        if (StringUtils.isNotBlank(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        Example example=new Example(SysConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configKey",configKey);
        SysConfig retConfig = configMapper.selectOneByExample(example);
        if (null!=retConfig)
        {
            redisOperator.set(Constants.SYS_CONFIG_KEY +configKey, retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }
}
