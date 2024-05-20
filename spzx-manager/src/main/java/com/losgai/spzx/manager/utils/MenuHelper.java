package com.losgai.spzx.manager.utils;

import cn.hutool.core.lang.tree.TreeUtil;
import com.losgai.spzx.model.entity.system.SysMenu;
import com.losgai.spzx.model.vo.system.SysMenuVo;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//封装树型菜单数据
public class MenuHelper { //列表数据转换工具类
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //TODO: 完成菜单列表数据封装，递归实现
        List<SysMenu> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (SysMenu sysMenu : sysMenuList) {
            //找到递归入口
            //基层菜单，条件：parent_id=0
            if (sysMenu.getParentId().longValue() ==0) {
                //根据第一层找下层数据，使用递归
                trees.add(findChildren(sysMenu, sysMenuList));
            }
        }
        return trees;
    }
    //递归查找下层菜单
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //SysMenu有属性List<SysMenu> children 封装下一层菜单
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu it : sysMenuList) {
            //判断sysMenu是否和sysMenuList中的parentId相同
            if (sysMenu.getId().longValue()==it.getParentId().longValue()) {
                //it 为下层数据，进行递归子菜单封装
                sysMenu.getChildren().add(findChildren(it, sysMenuList));
            }
        }
        return sysMenu;
    }
}
