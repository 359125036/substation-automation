package com.substation.controller.system;

import com.substation.controller.BaseController;
import com.substation.entity.system.SysDictData;
import com.substation.http.HttpResult;
import com.substation.page.TableDataInfo;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SysDictDataController
 * @Author: zhengxin
 * @Description: 字典数据信息
 * @Date: 2020/11/11 15:53
 * @Version: 1.0
 */
@Api(value = "字典数据信息",tags = {"系统：系统字典数据接口"})
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {

    public static final String BUSINESS_NAME = "字典数据管理";

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * @Method list
     * @Author zhengxin
     * @Description 查询字典数据列表
     * @Param: [dictData] 字典数据信息
     * @Return com.yddl.page.TableDataInfo
     * @Date 2020/12/3 11:47
     * @Version  1.0
     */
    @ApiOperation(value = "字典数据",httpMethod = "GET")
    @GetMapping("/list")
    public TableDataInfo list(
            @ApiParam(name = "dictData",value = "字典数据信息")
                    SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }
    /**
     * @Method dictType
     * @Author zhengxin
     * @Description 根据字典类型查询字典数据信息
     * @param dictType 字典类型信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/11 16:00
     */
    @ApiOperation(value = "字典类型查询字典数据信息",httpMethod = "GET")
    @GetMapping(value = "/type/{dictType}")
    public HttpResult dictType(
            @ApiParam(name = "dictType",value = "字典类型信息")
            @PathVariable String dictType) {
        return HttpResult.ok(dictDataService.selectDictDataByType(dictType));
    }

    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 根据字典数据id查询字典数据详细
     * @Param: [dictCode]
     * @Return com.yddl.http.HttpResult
     * @Date 2020/12/3 11:55
     * @Version  1.0
     */
    @ApiOperation(value = "根据字典数据id查询字典数据详细",httpMethod = "GET")
    @GetMapping(value = "/{dictCode}")
    public HttpResult getInfo(
            @ApiParam(name = "dictCode",value = "字典数据id")
            @PathVariable Long dictCode) {
        if(dictCode==null)
            HttpResult.error("字典数据id不能为空");
        return HttpResult.ok(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * @Method saveDictData
     * @Author zhengxin
     * @Description 新增字典数据
     * @Param: [dictData] 字典数据信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/12/3 13:47
     * @Version  1.0
     */
    @ApiOperation(value = "新增字典数据",httpMethod = "POST")
    @PostMapping("/saveDictData")
    public HttpResult saveDictData(
            @ApiParam(name = "dictData",value = "字典数据信息",required = true)
            @RequestBody SysDictData dictData) {

        dictData.setCreateBy(loginUserService.getUsername());
        return toAjax(dictDataService.insertDictData(dictData));
    }

    /**
     * @Method updateDictData
     * @Author zhengxin
     * @Description 修改字典数据
     * @Param: [dictData] 字典数据信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/12/3 13:56
     * @Version  1.0
     */
    @ApiOperation(value = "修改字典数据",httpMethod = "PUT")
    @PutMapping("/updateDictData")
    public HttpResult updateDictData(
            @ApiParam(name = "dictData",value = "字典数据信息",required = true)
            @RequestBody SysDictData dictData){

        dictData.setUpdateBy(loginUserService.getUsername());
        return toAjax(dictDataService.updateDictData(dictData));
    }

    /**
     * @Method remove
     * @Author zhengxin
     * @Description  批量删除字典数据信息
     * @Param: [dictCodes] 字典数据ID数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/12/3 14:03
     * @Version  1.0
     */
    @ApiOperation(value = "批量删除字典数据信息",httpMethod = "DELETE")
    @DeleteMapping("/{dictCodes}")
    public HttpResult remove(
            @ApiParam(name = "dictCodes",value = "字典数据ID数组",required = true)
            @PathVariable Long[] dictCodes) {

        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
