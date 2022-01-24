package com.substation.controller.system;

import com.substation.constant.UserConstants;
import com.substation.controller.BaseController;
import com.substation.entity.dto.SysDeptDto;
import com.substation.entity.system.SysDept;
import com.substation.http.HttpResult;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: SysDeptController
 * @Author: zhengxin
 * @Description: 部门信息
 * @Date: 2020/11/11 15:53
 * @Version: 1.0
 */
@Api(value = "部门信息",tags = {"系统：系统部门接口"})
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    public static final String BUSINESS_NAME = "部门管理";

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * @Method treeSelect
     * @Author zhengxin
     * @Description 获取部门下拉树列表
     * @param deptDto 部门信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:50
     */
    @ApiOperation(value = "获取部门下拉树列表",httpMethod ="GET")
    @GetMapping("/treeSelect")
    public HttpResult treeSelect(
            @ApiParam(name = "deptDto",value = "部门信息")
                    SysDeptDto deptDto) {
        List<SysDept> depts = deptService.selectDeptList(deptDto);
        return HttpResult.ok(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * @Method deptList
     * @Author zhengxin
     * @Description 获取部门列表
     * @param deptDto 部门信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:51
     */
    @ApiOperation(value = "获取部门列表",httpMethod ="GET")
    @GetMapping("/deptList")
    public HttpResult deptList(
            @ApiParam(name = "deptDto",value = "部门信息")
            SysDeptDto deptDto){
        List<SysDept> depts = deptService.selectDeptList(deptDto);
        return HttpResult.ok(depts);
    }

    /**
     * @Method excludeChild
     * @Author zhengxin
     * @Description 查询部门列表（排除节点）
     * @param deptId 部门ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:53
     */
    @ApiOperation(value = "查询部门列表（排除节点）",httpMethod ="GET")
    @GetMapping("/list/exclude/{deptId}")
    public HttpResult excludeChild(
            @ApiParam(name = "deptId",value = "部门ID")
            @PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDeptDto());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""))
            {
                it.remove();
            }
        }
        return HttpResult.ok(depts);
    }


    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 根据部门编号获取详细信息
     * @param deptId 部门ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:55
     */
    @ApiOperation(value = "根据部门编号获取详细信息",httpMethod ="GET")
    @GetMapping(value = "/{deptId}")
    public HttpResult getInfo(
            @ApiParam(name = "deptId",value = "部门ID")
            @PathVariable Long deptId) {
        return HttpResult.ok(deptService.selectDeptById(deptId));
    }

    /**
     * @Method saveDept
     * @Author zhengxin
     * @Description 新增部门
     * @param deptDto 部门信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:56
     */
    @ApiOperation(value = "新增部门",httpMethod ="POST")
    @PostMapping("/saveDept")
    public HttpResult saveDept(
            @ApiParam(name = "deptDto",value = "部门信息",required = true)
            @RequestBody SysDeptDto deptDto){
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(deptDto)))
            return HttpResult.error("新增部门'" + deptDto.getDeptName() + "'失败，部门名称已存在");
        deptDto.setCreateBy(loginUserService.getUsername());
       return toAjax(deptService.insertDept(deptDto));
    }

    /**
     * @Method updateDept
     * @Author zhengxin
     * @Description 修改部门
     * @param deptDto 部门信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 14:58
     */
    @ApiOperation(value = "修改部门",httpMethod ="PUT")
    @PutMapping("/updateDept")
    public HttpResult updateDept(
            @ApiParam(name = "deptDto",value = "部门信息",required = true)
            @RequestBody SysDeptDto deptDto){
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(deptDto)))
        {
            return HttpResult.error("修改部门'" + deptDto.getDeptName() + "'失败，部门名称已存在");
        }
        else if (deptDto.getParentId().equals(deptDto.getDeptId()))
        {
            return HttpResult.error("修改部门'" + deptDto.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, deptDto.getStatus())
                && deptService.selectNormalChildrenDeptById(deptDto.getDeptId()) > 0)
        {
            return HttpResult.error("该部门包含未停用的子部门！");
        }
        deptDto.setUpdateBy(loginUserService.getUsername());
        return toAjax(deptService.updateDept(deptDto));
    }

    /**
     * @Method delDept
     * @Author zhengxin
     * @Description 删除部门
     * @param deptId 部门ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:01
     */
    @ApiOperation(value = "删除部门",httpMethod = "DELETE")
    @DeleteMapping("/delDept/{deptId}")
    public HttpResult delDept(
            @ApiParam(name = "deptId",value = "部门ID",required = true)
            @PathVariable Long deptId){
        if(deptId==null)
            return HttpResult.error("部门ID不能为空");

        if (deptService.hasChildByDeptId(deptId))
            return HttpResult.error("存在下级部门,不允许删除");

        if (deptService.checkDeptExistUser(deptId))
            return HttpResult.error("部门存在用户,不允许删除");

        if(deptService.checkDeptExistEquip(deptId))
            return HttpResult.error("部门存在设备,不允许删除");

        return toAjax(deptService.deleteDeptById(deptId));
    }
}
