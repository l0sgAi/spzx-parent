package com.losgai.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.SysUserService;
import com.losgai.spzx.model.dto.system.AssginRoleDto;
import com.losgai.spzx.model.dto.system.SysUserDto;
import com.losgai.spzx.model.entity.system.SysUser;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    //1.用户条件分页查询
    //@Log(title = "用户管理:分页列表显示", businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping( "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //2.用户添加
    @Log(title = "用户管理:添加用户", businessType = 1,operatorType = OperatorType.MANAGE)
    @PostMapping( "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3.用户修改
    @Log(title = "用户管理:修改用户信息", businessType = 2,operatorType = OperatorType.MANAGE)
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //4.用户删除
    @Log(title = "用户管理:删除用户信息", businessType = 3,operatorType = OperatorType.MANAGE)
    @DeleteMapping( "/deleteSysUserById/{userId}")
    public Result deleteSysUserById(@PathVariable("userId") Long userId) {
        sysUserService.deleteSysUserById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //用户分配角色，保持分配数据
    @Log(title = "用户管理:分配用户角色", businessType = 0,operatorType = OperatorType.MANAGE)
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
