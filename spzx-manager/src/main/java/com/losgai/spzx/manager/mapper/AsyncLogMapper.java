package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper public interface AsyncLogMapper {
    void saveSysOperLog(SysOperLog sysOperLog);
}
