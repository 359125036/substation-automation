package com.substation.controller.system;

import com.substation.constant.UserConstants;
import com.substation.controller.BaseController;
import com.substation.entity.dto.SysConfigDto;
import com.substation.entity.system.SysConfig;
import com.substation.http.HttpResult;
import com.substation.page.TableDataInfo;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SysConfigController
 * @Author: zhengxin
 * @Description: 参数配置 信息操作处理
 * @Date: 2020/11/20 15:53
 * @Version: 1.0
 */
@Api(value = "参数配置",tags = {"系统：系统参数配置接口"})
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {

    public static final String BUSINESS_NAME = "参数配置管理";

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * @Method list
     * @Author zhengxin
     * @Description 获取参数配置列表
     * @param configDto 参数配置信息
     * @Return com.yddl.page.TableDataInfo
     * @Date 2020/11/25 9:58
     */
    @ApiOperation(value = "获取参数配置列表",httpMethod ="GET")
    @GetMapping("/list")
    public TableDataInfo list(
            @ApiParam(name = "configDto",value = "参数配置信息")
                    SysConfigDto configDto) {
        startPage();
        List<SysConfig> list = configService.selectConfigList(configDto);
        return getDataTable(list);
    }

    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 根据参数编号获取详细信息
     * @param configId 参数配置ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 10:30
     */
    @ApiOperation(value = "根据参数编号获取详细信息",httpMethod ="GET")
    @GetMapping(value = "/{configId}")
    public HttpResult getInfo(
            @ApiParam(name = "configId",value = "参数配置ID")
            @PathVariable Integer configId) {

        return HttpResult.ok(configService.selectConfigById(configId));
    }


    /**
     * 根据参数键名查询参数值
     */
    /**
     * @Method getConfigKey
     * @Author zhengxin
     * @Description 根据参数键名查询参数值
     * @param configKey 参数键名
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 17:30
     */
    @ApiOperation(value = "根据参数键名查询参数值",httpMethod ="GET")
    @GetMapping(value = "/configKey/{configKey}")
    public HttpResult getConfigKey(
            @ApiParam(name = "configKey",value = "参数键名")
            @PathVariable String configKey) {
        return HttpResult.ok(configService.selectConfigByKey(configKey));
    }

    /**
     * @Method saveConfig
     * @Author zhengxin
     * @Description 新增参数配置
     * @param configDto 参数配置信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 10:40
     */
    @ApiOperation(value = "新增参数配置",httpMethod ="POST")
    @PostMapping("/saveConfig")
    public HttpResult saveConfig(
            @ApiParam(name = "configDto",value = "参数配置信息",required = true)
            @RequestBody SysConfigDto configDto)
    {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(configDto)))
        {
            return HttpResult.error("新增参数'" + configDto.getConfigName() + "'失败，参数键名已存在");
        }
        configDto.setCreateBy(loginUserService.getUsername());
        return toAjax(configService.insertConfig(configDto));
    }

    /**
     * @Method updateConfig
     * @Author zhengxin
     * @Description 修改参数配置
     * @param configDto 参数配置信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 10:50
     */
    @ApiOperation(value = "修改参数配置",httpMethod ="PUT")
    @PutMapping("/updateConfig")
    public HttpResult updateConfig(
            @ApiParam(name = "configDto",value = "参数配置信息",required = true)
            @RequestBody SysConfigDto configDto) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(configDto))) {
            return HttpResult.error("修改参数'" + configDto.getConfigName() + "'失败，参数键名已存在");
        }
        configDto.setUpdateBy(loginUserService.getUsername());
        return toAjax(configService.updateConfig(configDto));
    }

    /**
     * @Method remove
     * @Author zhengxin
     * @Description 删除参数配置
     * @param configIds 参数配置ID数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 11:50
     */
    @ApiOperation(value = "删除参数配置",httpMethod ="DELETE")
    @DeleteMapping("/delConfig/{configIds}")
    public HttpResult remove(
            @ApiParam(name = "configIds",value = "参数配置ID数组",required = true)
            @PathVariable Long[] configIds) {
        return toAjax(configService.deleteConfigByIds(configIds));
    }

    /**
     * @Method clearCache
     * @Author zhengxin
     * @Description 清空缓存
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 11:53
     */
    @ApiOperation(value = "清空缓存",httpMethod ="DELETE")
    @DeleteMapping("/clearCache")
    public HttpResult clearCache() {
        configService.clearCache();
        return HttpResult.ok();
    }
}
