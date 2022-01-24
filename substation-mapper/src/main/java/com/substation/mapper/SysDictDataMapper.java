package com.substation.mapper;

import com.substation.entity.system.SysDictData;
import com.substation.my.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictDataMapper extends MyMapper<SysDictData> {

    /**
     * 同步修改字典类型
     *
     * @param oldDictType 旧字典类型
     * @param newDictType 新旧字典类型
     * @return 结果
     */
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);

    /**
     * 查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    public int countDictDataByType(String dictType);


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
