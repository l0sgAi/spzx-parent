package com.losgai.spzx.manager.service.impl;

import com.losgai.spzx.manager.mapper.SysMenuMapper;
import com.losgai.spzx.manager.mapper.SysRoleMenuMapper;
import com.losgai.spzx.manager.service.SysMenuService;
import com.losgai.spzx.manager.service.SysRoleMenuService;
import com.losgai.spzx.model.dto.system.AssginMenuDto;
import com.losgai.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuService.findNodes();

        //查询角色分配过的菜单列表
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        Map<String,Object> map = new HashMap<>();
        map.put("sysMenuList",sysMenuList);
        map.put("roleMenuIds",roleMenuIds);
        return map;
    }

    @Override
    public void deleteByRoleId(Long roleId) {

    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        //删除角色之前分配过的菜单数据
        sysRoleMenuMapper.deleteMenuByRoleId(assginMenuDto.getRoleId());

        //保存分配数据
        List<Map<String,Number>> menuInfo = assginMenuDto.getMenuIdList();
        if(menuInfo != null && !menuInfo.isEmpty()){ //角色分配了菜单
           sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }
}
