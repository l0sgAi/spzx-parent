package com.losgai.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.manager.mapper.SysRoleMapper;
import com.losgai.spzx.manager.service.SysRoleService;
import com.losgai.spzx.model.dto.system.SysRoleDto;
import com.losgai.spzx.model.entity.system.SysRole;
import com.losgai.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Override
    public PageInfo<SysRole> findByPage
            (SysRoleDto sysRoleDto,Integer current, Integer limit) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<SysRole> sysRoleList=sysRoleMapper.findByPage(sysRoleDto);
        //封装pageInfo对象
        PageInfo<SysRole> pageInfo=new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    @Override
    public void deleteSysRoleById(Long roleId) {
        sysRoleMapper.deleteSysRoleById(roleId);
    }

    @Override
    public Map<String, Object> findAllRoles() {
        ///1.查询所有角色
        List<SysRole> sysRoleList=sysRoleMapper.findAllRoles();
        //2.分配过的角色列表
        Map<String,Object> sysRoleMap=new HashMap<>();
        sysRoleMap.put("allRolesList",sysRoleList);
        return sysRoleMap;
    }
}
