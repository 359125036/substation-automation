package com.substation.controller.system;

import com.substation.constant.UserConstants;
import com.substation.controller.BaseController;
import com.substation.entity.dto.SysPostDto;
import com.substation.entity.system.SysPost;
import com.substation.http.HttpResult;
import com.substation.page.TableDataInfo;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SysPostController
 * @Author: zhengxin
 * @Description: 岗位信息操作处理
 * @Date: 2020/11/19 15:53
 * @Version: 1.0
 */
@Api(value = "岗位信息",tags = {"系统：系统岗位接口"})
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {

    public static final String BUSINESS_NAME = "岗位管理";

    @Autowired
    private ISysPostService postService;

    @Autowired
    private LoginUserService loginUserService;


    /**
     * @Method list
     * @Author zhengxin
     * @Description 查询岗位列表
     * @param postDto 岗位信息
     * @Return com.yddl.page.TableDataInfo
     * @Date 2020/11/24 15:18
     */
    @ApiOperation(value = "查询岗位列表",httpMethod = "GET")
    @GetMapping("/postList")
    public TableDataInfo list(
            @ApiParam(name = "postDto",value = "岗位信息")
                    SysPostDto postDto) {
        startPage();
        List<SysPost> list = postService.selectPostList(postDto);
        return getDataTable(list);
    }


    /**
     * @Method savePost
     * @Author zhengxin
     * @Description 新增岗位
     * @param postDto 岗位信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:20
     */
    @ApiOperation(value = "新增岗位",httpMethod = "POST")
    @PostMapping("/savePost")
    public HttpResult savePost(
            @ApiParam(name = "postDto",value = "岗位信息",required = true)
            @RequestBody SysPostDto postDto) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(postDto)))
        {
            return HttpResult.error("新增岗位'" + postDto.getPostName() + "'失败，岗位名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(postDto)))
        {
            return HttpResult.error("新增岗位'" + postDto.getPostName() + "'失败，岗位编码已存在");
        }
        postDto.setCreateBy(loginUserService.getUsername());
        return toAjax(postService.insertPost(postDto));
    }

    /**
     * @Method updatePost
     * @Author zhengxin
     * @Description 更新岗位
     * @param postDto 岗位信息
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:23
     */
    @ApiOperation(value = "更新岗位",httpMethod = "PUT")
    @PutMapping("/updatePost")
    public HttpResult updatePost(
            @ApiParam(name = "postDto",value = "岗位信息",required = true)
            @RequestBody SysPostDto postDto)
    {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(postDto)))
        {
            return HttpResult.error("修改岗位'" + postDto.getPostName() + "'失败，岗位名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(postDto)))
        {
            return HttpResult.error("修改岗位'" + postDto.getPostName() + "'失败，岗位编码已存在");
        }
        postDto.setUpdateBy(loginUserService.getUsername());
        return toAjax(postService.updatePost(postDto));
    }

    /**
     * @Method deletePost
     * @Author zhengxin
     * @Description 删除岗位
     * @param postIds 岗位ID数组
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:26
     */
    @ApiOperation(value = "删除岗位",httpMethod = "DELETE")
    @DeleteMapping("/deletePost/{postIds}")
    public HttpResult remove(
            @ApiParam(name = "postIds",value = "岗位ID数组",required = true)
            @PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * @Method getInfo
     * @Author zhengxin
     * @Description 根据岗位编号获取详细信息
     * @param postId 岗位ID
     * @Return com.yddl.http.HttpResult
     * @Date 2020/11/24 15:28
     */
    @ApiOperation(value = "根据岗位编号获取详细信息",httpMethod = "GET")
    @GetMapping("/{postId}")
    public HttpResult getInfo(
            @ApiParam(name = "postId",value = "岗位ID")
            @PathVariable Long postId)
    {
        return HttpResult.ok(postService.selectPostById(postId));
    }

}
