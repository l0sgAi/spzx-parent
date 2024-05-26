package com.losgai.spzx.common.log.annotation;

import com.losgai.spzx.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) //定义注解的使用位置 方法
@Retention(RetentionPolicy.RUNTIME) //指明修饰的注解的生存周期 运行时
public @interface Log {

    public String title();  //模块名称
    public OperatorType operatorType() default OperatorType.MANAGE; //操作人类别
    public int businessType(); //业务类型(0：其它,1：新增,2：修改,3：删除)
    public boolean isSaveRequestData() default true; //是否保存请求的参数
    public boolean isSaveResponseData() default true; //是否保存响应的参数
}

