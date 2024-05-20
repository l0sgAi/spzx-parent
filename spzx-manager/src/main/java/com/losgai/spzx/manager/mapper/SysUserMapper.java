package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.dto.system.SysUserDto;
import com.losgai.spzx.model.entity.system.SysUser;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);
    List<SysUser> findByPage(SysUserDto sysUserDto);
    void saveSysUser(SysUser sysUser);
    void updateSysUser(SysUser sysUser);
    void deleteSysUserById(Long userId);
    Integer selectCountByUserName(String userName);
}
