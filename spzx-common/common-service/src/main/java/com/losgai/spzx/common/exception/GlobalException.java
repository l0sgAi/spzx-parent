package com.losgai.spzx.common.exception;

import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //@Controller增强，统一配置管理
public class GlobalException {
    @ExceptionHandler(Exception.class) //全局异常处理方法
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义异常处理
    @ExceptionHandler(SpzxException.class)
    @ResponseBody
    public Result error(SpzxException e) {
        return Result.build(null, e.getResultCodeEnum());
    }

}
