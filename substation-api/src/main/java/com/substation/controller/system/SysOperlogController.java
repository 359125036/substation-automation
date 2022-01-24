package com.substation.controller.system;

import com.substation.controller.BaseController;
import com.substation.entity.dto.SysOperLogDto;
import com.substation.entity.system.SysOperLog;
import com.substation.http.HttpResult;
import com.substation.page.TableDataInfo;
import com.substation.service.system.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName:  SysOperlogController
 * @Author: zhengxin
 * @Description: 操作日志记录
 * @Date: 2020/11/25 10:25
 * @Version: 1.0
 */
@Api(value = "操作日志",tags = {"系统：系统操作日志接口"})
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {

    public static final String BUSINESS_NAME = "操作日志管理";

    @Autowired
    private ISysOperLogService operLogService;

    /**
     * @Method list
     * @Author zhengxin
     * @Description 获取操作日志列表
     * @param operLogDto 操作日志信息
     * @Return com.yddl.page.TableDataInfo
     * @Date 2020/11/25 10:28
     */
    @ApiOperation(value = "获取操作日志列表",httpMethod ="GET")
    @GetMapping("/list")
    public TableDataInfo list(
            @ApiParam(name = "operLogDto",value = "操作日志信息")
                    SysOperLogDto operLogDto) {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLogDto);
        return getDataTable(list);
    }

    /**
     * @Method delOperLog
     * @Author zhengxin
     * @Description 删除操作日志
     * @param operIds 操作日志ID数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 10:37
     */
    @ApiOperation(value = "删除操作日志",httpMethod ="DELETE")
    @DeleteMapping("/delOperLog/{operIds}")
    public HttpResult delOperLog(
            @ApiParam(name = "operIds",value = "操作日志ID数组")
            @PathVariable String[] operIds) {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    /**
     * @Method clean
     * @Author zhengxin
     * @Description 清空操作日志
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/25 11:37
     */
    @ApiOperation(value = "清空操作日志",httpMethod ="DELETE")
    @DeleteMapping("/clean")
    public HttpResult clean() {
        operLogService.cleanOperLog();
        return HttpResult.ok();
    }

}
