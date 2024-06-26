package com.losgai.spzx.manager.controller;

import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.SysMenuService;
import com.losgai.spzx.model.entity.system.SysMenu;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //获取所有菜单列表
    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        System.out.println("返回的树型表：list = " + list);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    //菜单添加
    @Log(title = "菜单管理:添加", businessType = 1,operatorType = OperatorType.MANAGE)
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.addMenu(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //菜单修改
    @Log(title = "菜单管理:修改", businessType = 2,operatorType = OperatorType.MANAGE)
    @PutMapping("/updateMenu")
    public Result updateMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateMenu(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //菜单删除
    @Log(title = "菜单管理:删除", businessType = 3,operatorType = OperatorType.MANAGE)
    @DeleteMapping("/removeMenu/{id}")
    public Result removeMenu(@PathVariable("id") Long id) {
        sysMenuService.removeMenu(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
