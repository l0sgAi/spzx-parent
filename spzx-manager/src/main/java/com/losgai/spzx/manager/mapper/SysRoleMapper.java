package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.dto.system.SysRoleDto;
import com.losgai.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteSysRoleById(Long roleId);

    List<SysRole> findAllRoles();
}
