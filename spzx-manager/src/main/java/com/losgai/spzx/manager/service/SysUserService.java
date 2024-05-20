package com.losgai.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.system.AssginRoleDto;
import com.losgai.spzx.model.dto.system.LoginDto;
import com.losgai.spzx.model.dto.system.SysUserDto;
import com.losgai.spzx.model.entity.system.SysUser;
import com.losgai.spzx.model.vo.system.LoginVo;
import org.springframework.stereotype.Service;

public interface SysUserService {
    LoginVo login(LoginDto loginDto); //登录

    SysUser getUserInfo(String token); //获取用户信息

    void logout(String token); //退出

    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize); //分页查询

    void saveSysUser(SysUser sysUser); //插入用户

    void updateSysUser(SysUser sysUser); //更新用户

    void deleteSysUserById(Long userId); //删除用户

    void doAssign(AssginRoleDto assginRoleDto);
}
