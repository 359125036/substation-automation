package com.substation.service.system;

import com.substation.entity.system.SysDictData;

import java.util.List;

/**
 * @ClassName: ISysDictDataService
 * @Author: zhengxin
 * @Description: 数据字典 业务层
 * @Date: 2020/11/19 11:03
 * @Version: 1.0
 */
public interface ISysDictDataService {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * @Method selectDictDataList
     * @Author zhengxin
     * @Description 根据条件分页查询字典数据
     * @Param: [dictData] 字典数据信息
     * @Return java.util.List<SysDictData>
     * @Date 2020/12/3 11:49
     * @Version  1.0
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);


    /**
     * @Method selectDictDataById
     * @Author zhengxin
     * @Description 根据字典数据ID查询信息
     * @Param: [dictCode] 字典数据ID
     * @Return SysDictData
     * @Date 2020/12/3 11:57
     * @Version  1.0
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * @Method insertDictData
     * @Author zhengxin
     * @Description 新增字典数据
     * @Param: [dictData]  字典数据
     * @Return int
     * @Date 2020/12/3 13:48
     * @Version  1.0
     */
    public int insertDictData(SysDictData dictData);

    /**
     * @Method updateDictData
     * @Author zhengxin
     * @Description 修改保存字典数据信息
     * @Param: [dictData] 字典数据信息
     * @Return int
     * @Date 2020/12/3 13:57
     * @Version  1.0
     */
    public int updateDictData(SysDictData dictData);

    /**
     * @Method deleteDictDataByIds
     * @Author zhengxin
     * @Description 批量删除字典数据信息
     * @Param: [dictCodes] 需要删除的字典数据ID
     * @Return int
     * @Date 2020/12/3 14:02
     * @Version  1.0
     */
    public int deleteDictDataByIds(Long[] dictCodes);
}
