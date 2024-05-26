package com.losgai.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.SysRoleService;
import com.losgai.spzx.model.dto.system.SysRoleDto;
import com.losgai.spzx.model.entity.system.SysRole;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //1.角色列表方法
    //当前页current和每页的记录数limit
    @PostMapping("/findByPage/{current}/{limit}")
    //当前页current和每页的记录数limit
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        PageInfo<SysRole> pageInfo=sysRoleService.findByPage(sysRoleDto,current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //2.角色添加的方法
    @Log(title = "角色管理:添加", businessType = 1,operatorType = OperatorType.MANAGE)
    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3.角色修改方法
    @Log(title = "角色管理:修改", businessType = 2,operatorType = OperatorType.MANAGE)
    @PutMapping ("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //4.角色删除方法
    @Log(title = "角色管理:删除", businessType = 3,operatorType = OperatorType.MANAGE)
    @DeleteMapping("/deleteSysRoleById/{roleId}")
    public Result deleteSysRoleById(@PathVariable("roleId") Long roleId) {
        sysRoleService.deleteSysRoleById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查询所有角色
    @GetMapping("/findAllRoles")
    public Result findAll() {
        Map<String,Object> sysRoleMap = sysRoleService.findAllRoles();
        return Result.build(sysRoleMap, ResultCodeEnum.SUCCESS);
    }
}
