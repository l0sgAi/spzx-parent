package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    List<Long> findSysRoleMenuByRoleId(Long roleId);

    void deleteMenuByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);

    void setParentIsHalf(Long menuId); //设置父菜单状态为半开
}
