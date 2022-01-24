package com.substation.service.system.impl;

import com.substation.constant.UserConstants;
import com.substation.entity.system.SysDictType;
import com.substation.exception.ValidatorException;
import com.substation.mapper.SysDictDataMapper;
import com.substation.mapper.SysDictTypeMapper;
import com.substation.service.system.ISysDictTypeService;
import com.substation.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: SysDictTypeServiceImpl
 * @Author: zhengxin
 * @Description: 字典类型 业务层
 * @Date: 2020/11/19 11:03
 * @Version: 1.0
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;
    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String checkDictTypeUnique(SysDictType dictType) {
        Long dictId = dictType.getDictId()==null ? -1L : dictType.getDictId();
        Example example=new Example(SysDictType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dictType",dictType.getDictType());
        SysDictType sysDictType = dictTypeMapper.selectOneByExample(example);
        if (null!=sysDictType && sysDictType.getDictId().longValue() != dictId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertDictType(SysDictType dictType) {
        dictType.setCreateTime(new Date());
        int row = dictTypeMapper.insertSelective(dictType);
        if(row>0)
            //清空字典缓存
            DictUtils.clearDictCache();
        return row;
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateDictType(SysDictType dictType) {
        //根据id获取历史字典类型信息
        SysDictType oldDict = dictTypeMapper.selectByPrimaryKey(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        dictType.setUpdateTime(new Date());
        int row = dictTypeMapper.updateByPrimaryKeySelective(dictType);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectByPrimaryKey(dictId);
    }

    /**
     * 批量删除字典信息
     *
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0)
            {
                throw new ValidatorException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }
        int count = dictTypeMapper.deleteDictTypeByIds(dictIds);
        if (count > 0)
        {
            DictUtils.clearDictCache();
        }
        return count;
    }
}
