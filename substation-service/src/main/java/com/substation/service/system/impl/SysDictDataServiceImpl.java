package com.substation.service.system.impl;

import com.substation.entity.system.SysDictData;
import com.substation.mapper.SysDictDataMapper;
import com.substation.service.system.ISysDictDataService;
import com.substation.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: SysDictDataServiceImpl
 * @Author: zhengxin
 * @Description: 数据字典 业务层
 * @Date: 2020/11/19 11:03
 * @Version: 1.0
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if (null != dictDatas) {
            return dictDatas;
        }
        Example example = new Example(SysDictData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dictType", dictType);
        criteria.andEqualTo("status", "0");
        dictDatas = dictDataMapper.selectByExample(example);
        if (null != dictDatas) {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }

    /**
     * @Method selectDictDataList
     * @Author zhengxin
     * @Description 根据条件分页查询字典数据
     * @Param: [dictData] 字典数据信息
     * @Return java.util.List<SysDictData>
     * @Date 2020/12/3 11:49
     * @Version 1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * @Method selectDictDataById
     * @Author zhengxin
     * @Description 根据字典数据ID查询信息
     * @Param: [dictCode] 字典数据ID
     * @Return SysDictData
     * @Date 2020/12/3 11:57
     * @Version 1.0
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectByPrimaryKey(dictCode);
    }

    /**
     * @Method insertDictData
     * @Author zhengxin
     * @Description 新增字典数据
     * @Param: [dictData]  字典数据
     * @Return int
     * @Date 2020/12/3 13:48
     * @Version 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertDictData(SysDictData dictData) {
        dictData.setCreateTime(new Date());
        //新增字典数据
        int row = dictDataMapper.insert(dictData);
        //清空缓存
        if (row > 0)
            DictUtils.clearDictCache();
        return row;
    }

    /**
     * @Method updateDictData
     * @Author zhengxin
     * @Description 修改保存字典数据信息
     * @Param: [dictData] 字典数据信息
     * @Return int
     * @Date 2020/12/3 13:57
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateDictData(SysDictData dictData) {
        dictData.setUpdateTime(new Date());
        //修改保存字典数据信息
        int row = dictDataMapper.updateByPrimaryKeySelective(dictData);
        if(row>0)
            DictUtils.clearDictCache();
        return row;
    }

    /**
     * @Method deleteDictDataByIds
     * @Author zhengxin
     * @Description 批量删除字典数据信息
     * @Param: [dictCodes] 需要删除的字典数据ID
     * @Return int
     * @Date 2020/12/3 14:02
     * @Version  1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteDictDataByIds(Long[] dictCodes) {
        int row = dictDataMapper.deleteDictDataByIds(dictCodes);
        if(row>0)
            DictUtils.clearDictCache();
        return row;
    }
}
