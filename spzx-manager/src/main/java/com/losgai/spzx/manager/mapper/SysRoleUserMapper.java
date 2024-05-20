package com.losgai.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleUserMapper {

    void deleteByUserId(Long userId);

    void doAssign(Long userId, Long roleId);
}
