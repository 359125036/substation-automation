package com.substation.service.system.impl;

import com.substation.constant.UserConstants;
import com.substation.entity.dto.SysPostDto;
import com.substation.entity.system.SysPost;
import com.substation.exception.ValidatorException;
import com.substation.mapper.SysPostMapper;
import com.substation.mapper.SysUserPostMapper;
import com.substation.service.system.ISysPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: SysPostServiceImpl
 * @Author: zhengxin
 * @Description: 岗位 业务层处理
 * @Date: 2020/11/16 17:15
 * @Version: 1.0
 */
@Service
public class SysPostServiceImpl implements ISysPostService {

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysPost> selectAllPost() {
        return postMapper.selectAll();
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Integer> selectPostListByUserId(Long userId) {
        return postMapper.selectPostListByUserId(userId);
    }


    /**
     * 查询岗位信息集合
     *
     * @param postDto 岗位信息
     * @return 岗位列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysPost> selectPostList(SysPostDto postDto) {
        SysPost post=new SysPost();
        BeanUtils.copyProperties(postDto,post);
        return postMapper.selectPostList(post);
    }


    /**
     * 校验岗位名称
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String checkPostNameUnique(SysPostDto postDto) {
        Long postId = postDto.getPostId()==null ? -1L : postDto.getPostId();
        SysPost sysPost=new SysPost();
        sysPost.setPostName(postDto.getPostName());
        SysPost info = postMapper.selectOne(sysPost);
        if (null!=info && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 校验岗位编码
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String checkPostCodeUnique(SysPostDto postDto) {
        Long postId = postDto.getPostId()==null ? -1L : postDto.getPostId();
        SysPost sysPost=new SysPost();
        sysPost.setPostCode(postDto.getPostCode());
        SysPost info = postMapper.selectOne(sysPost);
        if (null!=info && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 新增保存岗位信息
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertPost(SysPostDto postDto) {
        SysPost post=new SysPost();
        BeanUtils.copyProperties(postDto,post);
        post.setCreateTime(new Date());
        return postMapper.insert(post);
    }

    /**
     * 修改保存岗位信息
     *
     * @param postDto 岗位信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updatePost(SysPostDto postDto) {
        SysPost post=new SysPost();
        BeanUtils.copyProperties(postDto,post);
        post.setUpdateTime(new Date());
        return postMapper.updateByPrimaryKeySelective(post);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deletePostByIds(Long[] postIds) {
        {
            for (Long postId : postIds) {

                //根据岗位id获取岗位信息
                SysPost post = postMapper.selectByPrimaryKey(postId);
                //根据岗位id查询绑定用户数量
                int userCount = userPostMapper.countUserPostById(postId);
                if (userCount > 0) {
                    throw new ValidatorException(String.format("%1$s已分配,不能删除", post.getPostName()));
                }
            }
            return postMapper.deletePostByIds(postIds);
        }
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysPost selectPostById(Long postId) {
        return postMapper.selectByPrimaryKey(postId);
    }
}
