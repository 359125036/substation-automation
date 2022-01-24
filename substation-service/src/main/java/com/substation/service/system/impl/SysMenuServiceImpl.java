package com.substation.service.system.impl;

import com.substation.constant.UserConstants;
import com.substation.entity.dto.SysMenuDto;
import com.substation.entity.system.SysMenu;
import com.substation.entity.system.SysUser;
import com.substation.entity.vo.MetaVo;
import com.substation.entity.vo.RouterVo;
import com.substation.entity.vo.TreeSelect;
import com.substation.mapper.SysMenuMapper;
import com.substation.mapper.SysRoleMenuMapper;
import com.substation.service.system.ISysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: SysMenuServiceImpl
 * @Author: zhengxin
 * @Description: 菜单 业务层处理
 * @Date: 2020/11/16 17:05
 * @Version: 1.0
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    /**
     * 根据用户查询权限
     *
     * @param user 用户
     * @return 权限列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Set<String> selectMenuPermsByUser(SysUser user) {
        Set<String> permsSet = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
            permsSet.add("*:*:*");

        List<String> perms=menuMapper.selectMenuPermsByUserId(user.getUserId());
        for (String perm : perms) {
            if (StringUtils.isNoneBlank(perm))
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param user 用户
     * @return 菜单列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysMenu> selectMenuTreeByUserId(SysUser user) {
        List<SysMenu> menus = null;
        if (user.isAdmin())
            menus = menuMapper.selectMenuTreeAll();
        else
            menus = menuMapper.selectMenuTreeByUserId(user.getUserId());
        return getChildPerms(menus, 0);
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        List<SysMenu> menuList = selectMenuList(new SysMenuDto(), userId);
        return menuList;
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menuDto 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysMenu> selectMenuList(SysMenuDto menuDto, Long userId) {
        List<SysMenu> menuList = null;
        Map<String,Object> map=new HashMap<>();
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)) {
            Example example=new Example(SysMenu.class);
            Example.Criteria criteria = example.createCriteria();
            if(StringUtils.isNotBlank(menuDto.getMenuName()))
                criteria.andLike("menuName","%"+menuDto.getMenuName()+"%");
            criteria.andEqualTo("status",menuDto.getStatus());
            menuList = menuMapper.selectByExample(example);
        }
        else {
            map.put("userId", userId);
            map.put("menu", menuDto);
            menuList = menuMapper.selectMenuListByUserId(map);
        }
        return menuList;
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }


    /**
     * 校验菜单名称是否唯一
     *
     * @param menuDto 菜单信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String checkMenuNameUnique(SysMenuDto menuDto) {
        Long menuId = null==menuDto.getMenuId() ? -1L : menuDto.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menuDto.getMenuName(), menuDto.getParentId());
        if (null!=info && info.getMenuId().longValue() != menuId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 新增保存菜单信息
     *
     * @param menuDto 菜单信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertMenu(SysMenuDto menuDto) {
        SysMenu menu=new SysMenu();
        BeanUtils.copyProperties(menuDto,menu);
        menu.setCreateTime(new Date());
        return menuMapper.insert(menu);
    }


    /**
     * 修改保存菜单信息
     *
     * @param menuDto 菜单信息
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateMenu(SysMenuDto menuDto) {
        SysMenu menu=new SysMenu();
        BeanUtils.copyProperties(menuDto,menu);
        menu.setUpdateTime(new Date());
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        return roleMenuMapper.checkMenuExistRole(menuId)>0 ? true : false;
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteByPrimaryKey(menuId);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */

    private List<SysMenu> buildMenuTree(List<SysMenu> menus)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0)
            {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }
    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMeunFrame(menu))
            {
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMeunFrame(menu))
        {
            component = menu.getComponent();
        }
        return component;
    }


    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu)
    {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME==menu.getIsFrame())
        {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMeunFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }


    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMeunFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMeunFrame(SysMenu menu)
    {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext())
                {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }


    @Override
    public List<Integer> selectMenuListByRoleId(Long roleId) {
        return menuMapper.selectMenuListByRoleId(roleId);
    }
}
