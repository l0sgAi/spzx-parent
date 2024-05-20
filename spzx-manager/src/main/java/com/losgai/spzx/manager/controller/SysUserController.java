package com.losgai.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
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
    @GetMapping( "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //2.用户添加
    @PostMapping( "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3.用户修改
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //4.用户删除
    @DeleteMapping( "/deleteSysUserById/{userId}")
    public Result deleteSysUserById(@PathVariable("userId") Long userId) {
        sysUserService.deleteSysUserById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //用户分配角色，保持分配数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
