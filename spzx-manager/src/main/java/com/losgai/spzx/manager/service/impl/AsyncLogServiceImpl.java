package com.losgai.spzx.manager.service.impl;

import com.losgai.spzx.common.log.service.AsyncLogService;
import com.losgai.spzx.manager.mapper.AsyncLogMapper;
import com.losgai.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncLogServiceImpl implements AsyncLogService {

    @Autowired
    private AsyncLogMapper asyncLogMapper;

    //保存日志数据
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        //防止返回json数据过长，对sysOper内的JsonResult进行处理
        //数据长度限制为5000
        sysOperLog.setJsonResult(
                sysOperLog.getJsonResult().length()
                        > 4999 ? sysOperLog.getJsonResult().substring(0, 4999) :
                        sysOperLog.getJsonResult());
        asyncLogMapper.saveSysOperLog(sysOperLog);
    }
}
