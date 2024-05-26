package com.losgai.spzx.common.log.service;

import com.losgai.spzx.model.entity.system.SysOperLog;

public interface AsyncLogService { //保存日志的方法
    public abstract void saveSysOperLog(SysOperLog sysOperLog);
}
