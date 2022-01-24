package com.substation.controller.system;

import com.substation.constant.UserConstants;
import com.substation.controller.BaseController;
import com.substation.entity.system.SysDictType;
import com.substation.http.HttpResult;
import com.substation.page.TableDataInfo;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysDictTypeService;
import com.substation.utils.DictUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SysDictTypeController
 * @Author: zhengxin
 * @Description: 字典类型信息
 * @Date: 2020/11/11 11:53
 * @Version: 1.0
 */
@Api(value = "字典类型信息",tags = {"系统：系统字典类型接口"})
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    public static final String BUSINESS_NAME = "字典类型管理";

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * @Method list
     * @Author zhengxin
     * @Description 获取字典类型列表
     * @param dictType 字典类型信息
     * @Return com.yddl.page.TableDataInfo
     * @Date 2020/11/11 15:43
     */
    @ApiOperation(value = "获取字典类型列表",httpMethod = "GET")
    @GetMapping("/list")
    public TableDataInfo list(
            @ApiParam(name = "dictType",value = "字典类型信息")
                    SysDictType dictType)
    {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    /**
     * @Method saveDictType
     * @Author zhengxin
     * @Description 新增字典类型
     * @param dict 字典类型信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/11 15:46
     */
    @ApiOperation(value = "新增字典类型",httpMethod = "POST")
    @PostMapping("/saveDictType")
    public HttpResult saveDictType(
            @ApiParam(name = "dict",value = "字典类型信息")
            @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return HttpResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(loginUserService.getUsername());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * @Method edit
     * @Author zhengxin
     * @Description 修改字典类型
     * @param dict 字典类型信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/11 15:48
     */
    @ApiOperation(value = "修改字典类型",httpMethod = "PUT")
    @PutMapping("/updateDictType")
    public HttpResult edit(
            @ApiParam(name = "dict",value = "字典类型信息")
            @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return HttpResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(loginUserService.getUsername());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 查询字典类型详细
     * @param dictId 字典类型I
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/11 15:48
     */
    @ApiOperation(value = "查询字典类型详细",httpMethod = "GET")
    @GetMapping(value = "/{dictId}")
    public HttpResult getInfo(
            @ApiParam(name = "dictId",value = "字典类型ID",required = true)
            @PathVariable Long dictId) {
        return HttpResult.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * @Method remove
     * @Author zhengxin
     * @Description 删除字典类型
     * @param dictIds 字典类型ID数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/11 15:55
     */
    @ApiOperation(value = "查询字典类型详细",httpMethod = "DELETE")
    @DeleteMapping("/{dictIds}")
    public HttpResult remove(
            @ApiParam(name = "dictIds",value = "字典类型ID数组",required = true)
            @PathVariable Long[] dictIds) {
        return toAjax(dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * @Method clearCache
     * @Author zhengxin
     * @Description 清空缓存数据
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/11 15:57
     */
    @ApiOperation(value = "清空缓存数据",httpMethod = "DELETE")
    @DeleteMapping("/clearCache")
    public HttpResult clearCache() {
        DictUtils.clearDictCache();
        return HttpResult.ok();
    }

}
