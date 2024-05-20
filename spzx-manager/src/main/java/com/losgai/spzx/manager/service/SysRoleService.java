package com.losgai.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.system.SysRoleDto;
import com.losgai.spzx.model.entity.system.SysRole;
import com.losgai.spzx.model.vo.common.Result;

import java.util.Map;

public interface SysRoleService {
    //查询角色列表的方法
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto,Integer current, Integer limit);
    //添加角色方法
    void saveSysRole(SysRole sysRole);
    //修改角色的方法
    void updateSysRole(SysRole sysRole);
    //删除角色的方法
    void deleteSysRoleById(Long roleId);

    Map<String, Object> findAllRoles();
}
