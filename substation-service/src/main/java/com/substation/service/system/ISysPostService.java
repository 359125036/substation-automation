package com.substation.service.system;

import com.substation.entity.dto.SysPostDto;
import com.substation.entity.system.SysPost;

import java.util.List;

/**
 * @ClassName: ISysPostService
 * @Author: zhengxin
 * @Description: 岗位信息 服务层
 * @Date: 2020/11/16 17:03
 * @Version: 1.0
 */
public interface ISysPostService {

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    public List<SysPost> selectAllPost();

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    public List<Integer> selectPostListByUserId(Long userId);

    /**
     * 查询岗位信息集合
     *
     * @param postDto 岗位信息
     * @return 岗位列表
     */
    public List<SysPost> selectPostList(SysPostDto postDto);


    /**
     * 校验岗位名称
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    public String checkPostNameUnique(SysPostDto postDto);

    /**
     * 校验岗位编码
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    public String checkPostCodeUnique(SysPostDto postDto);


    /**
     * 新增保存岗位信息
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    public int insertPost(SysPostDto postDto);


    /**
     * 修改保存岗位信息
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    public int updatePost(SysPostDto postDto);

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deletePostByIds(Long[] postIds);


    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    public SysPost selectPostById(Long postId);

}
