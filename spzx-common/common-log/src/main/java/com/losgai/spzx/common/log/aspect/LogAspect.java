package com.losgai.spzx.common.log.aspect;

import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.service.AsyncLogService;
import com.losgai.spzx.common.log.utils.LogUtil;
import com.losgai.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect { //日志切点

    @Autowired
    private AsyncLogService asynsLogService;
    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){

      /*  String title = sysLog.title();
        System.out.println("title = " + title);
        int i = sysLog.businessType();
        System.out.println("i = " + i);*/

        //业务方法 调用之前封装数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            //业务调用后封装数据
            LogUtil.afterHandlLog(sysLog, proceed, sysOperLog, 0, null);
//            System.out.println("在业务方法之后执行...");
        }catch (Throwable e) {
            //异常处理
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog, null, sysOperLog, 1, e.getMessage());
            throw new RuntimeException(e);
        }

        //调用service将日志添加到数据库
        asynsLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }
}
