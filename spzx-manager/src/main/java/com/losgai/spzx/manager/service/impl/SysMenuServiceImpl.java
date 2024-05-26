package com.losgai.spzx.manager.service.impl;

import com.losgai.spzx.common.exception.SpzxException;
import com.losgai.spzx.manager.mapper.SysMenuMapper;
import com.losgai.spzx.manager.mapper.SysRoleMenuMapper;
import com.losgai.spzx.manager.service.SysMenuService;
import com.losgai.spzx.manager.utils.MenuHelper;
import com.losgai.spzx.model.entity.system.SysMenu;
import com.losgai.spzx.model.entity.system.SysUser;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.system.SysMenuVo;
import com.losgai.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Override
    public List<SysMenu> findNodes() {
        //1.查询所有菜单，返回List集合
        List<SysMenu> sysMenuList = sysMenuMapper.findAllMenus();
        if(CollectionUtils.isEmpty(sysMenuList)){ //sysMenuList为空
            return null;
        }
        //2.将集合的数据格式转换为符合element-plus的格式
        List<SysMenu> list = MenuHelper.buildTree(sysMenuList);
        return list;
    }

    @Override
    public void addMenu(SysMenu sysMenu) { //添加菜单
        sysMenuMapper.addMenu(sysMenu);
        //新添加菜单后，把isHalf改为半开状态(isHalf = 1)
        updateSysRoleMenu(sysMenu);
    }

    private void updateSysRoleMenu(SysMenu sysMenu) {
       //获取当前添加菜单的父菜单
        SysMenu parentMenu = sysMenuMapper.selectParentMenu(sysMenu.getParentId());
        if(parentMenu != null){ //父菜单不为空，设置为半开
           sysRoleMenuMapper.setParentIsHalf(parentMenu.getId());
            updateSysRoleMenu(parentMenu); //递归调用
        }
    }

    @Override
    public void updateMenu(SysMenu sysMenu) {
        sysMenuMapper.updateMenuById(sysMenu);
    }

    @Override
    public void removeMenu(Long id) {
        //根据当前菜单是否有子菜单，进行是否可以删除的判断
        //如果没有子菜单，直接删除
        if(sysMenuMapper.selectCountByParentId(id) == 0){
            sysMenuMapper.removeMenuById(id);
        }else{
            //如果有子菜单，不能删除
            throw new SpzxException(ResultCodeEnum.NODE_ERROR);
        }
    }

    //查询用户可操作菜单，并封装成前端路由形式
    @Override
    public List<SysMenuVo> findMenuByUserId() {
        //获取当前登录的用户ID
        SysUser sysUser = AuthContextUtil.getUser(); //从线程池中取出登录用户对象
        System.out.println("=========用户======== \n" + sysUser);
        Long userId = sysUser.getId();


        //根据ID查找操作菜单
        List<SysMenu> sysMenuList = sysMenuMapper.findMenusByUserId(userId);
        //操作菜单封装成前端路由形式返回
        List<SysMenu> buildedList = MenuHelper.buildTree(sysMenuList);

        return this.buildMenus(buildedList);
    }

    //将 List<SysMenu>转换成List<SysMenuVo>对象，保持相同结构
    private List<SysMenuVo> buildMenus(List<SysMenu> menus){
        List<SysMenuVo> sysMenuVoList = new LinkedList<>();
        for(SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
