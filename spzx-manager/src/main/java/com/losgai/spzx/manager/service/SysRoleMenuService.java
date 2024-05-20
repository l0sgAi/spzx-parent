package com.losgai.spzx.manager.service;

import com.losgai.spzx.model.dto.system.AssginMenuDto;

import java.util.List;
import java.util.Map;

public interface SysRoleMenuService {
    Map<String,Object> findSysRoleMenuByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
