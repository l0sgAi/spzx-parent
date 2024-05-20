package com.losgai.spzx.manager.service;

import com.losgai.spzx.model.entity.system.SysMenu;
import com.losgai.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> findNodes();

    void addMenu(SysMenu sysMenu);

    void updateMenu(SysMenu sysMenu);

    void removeMenu(Long id);

    List<SysMenuVo> findMenuByUserId();
}
