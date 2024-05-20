package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAllMenus();

    void addMenu(SysMenu sysMenu);

    void updateMenuById(SysMenu sysMenu);

    void removeMenuById(Long id);

    int selectCountByParentId(Long id);

    List<SysMenu> findMenusByUserId(Long userId);

    SysMenu selectParentMenu(Long parentId);
}
