package com.substation.controller.system;

import com.substation.controller.BaseController;
import com.substation.entity.dto.SysLoginInfoDto;
import com.substation.entity.system.SysLoginInfo;
import com.substation.http.HttpResult;
import com.substation.page.TableDataInfo;
import com.substation.service.system.ISysLoginInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SysLoginInfoController
 * @Author: zhengxin
 * @Description: 系统登录记录
 * @Date: 2020/11/25 15:07
 * @Version: 1.0
 */
@Api(value = "系统登录记录",tags = {"系统：系统登录记录接口"})
@RestController
@RequestMapping("/monitor/loginInfo")
public class SysLoginInfoController extends BaseController {

    public static final String BUSINESS_NAME = "登录记录管理";

    @Autowired
    private ISysLoginInfoService loginInfoService;

    /**
     * @Method list
     * @Author zhengxin
     * @Description 获取登录记录列表
     * @param loginInfoDto 登录日志对象
     * @Return com.yddl.page.TableDataInfo
     * @Date 2020/11/25 15:18
     */
    @ApiOperation(value = "获取登录记录列表",httpMethod ="GET")
    @GetMapping("/list")
    public TableDataInfo list(
            @ApiParam(name = "loginInfoDto",value = "登录日志对象")
                    SysLoginInfoDto loginInfoDto) {
        startPage();
        List<SysLoginInfo> list = loginInfoService.selectLoginInfoList(loginInfoDto);
        return getDataTable(list);
    }

    /**
     * @Method remove
     * @Author zhengxin
     * @Description 删除登录记录
     * @param infoIds 访问id数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 15:28
     */
    @ApiOperation(value = "删除登录记录",httpMethod ="DELETE")
    @DeleteMapping("/{infoIds}")
    public HttpResult remove(
            @PathVariable Long[] infoIds) {
        return toAjax(loginInfoService.deleteLoginInfoByIds(infoIds));
    }


    /**
     * @Method clean
     * @Author zhengxin
     * @Description 清空系统登录日志
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 15:30
     */
    @ApiOperation(value = "清空系统登录日志",httpMethod ="DELETE")
    @DeleteMapping("/clean")
    public HttpResult clean() {
        loginInfoService.cleanLoginInfo();
        return HttpResult.ok();
    }
}
