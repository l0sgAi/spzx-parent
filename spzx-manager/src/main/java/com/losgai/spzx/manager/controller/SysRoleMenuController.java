package com.losgai.spzx.manager.controller;

import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.SysRoleMenuService;
import com.losgai.spzx.model.dto.system.AssginMenuDto;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
     //1.查询所有菜单，并查询角色分配过的菜单id列表
    @GetMapping ("/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId) {
        Map<String, Object> menuIdMap = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(menuIdMap, ResultCodeEnum.SUCCESS);
    }

    //2.保存角色分配的菜单数据
    @Log(title = "角色管理:分配菜单", businessType = 0,operatorType = OperatorType.MANAGE)
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
