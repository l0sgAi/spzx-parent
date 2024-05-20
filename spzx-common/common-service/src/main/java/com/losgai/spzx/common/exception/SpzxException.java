package com.losgai.spzx.common.exception;

import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpzxException extends RuntimeException{
    private Integer code; //状态码
    private String message; //信息
    private ResultCodeEnum resultCodeEnum; //状态码枚举

    public SpzxException(ResultCodeEnum resultCodeEnum) { //构造方法
        this.resultCodeEnum=resultCodeEnum;
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }
}
