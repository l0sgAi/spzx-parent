package com.losgai.spzx.common.log.annotation;

import com.losgai.spzx.common.log.aspect.LogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) //定义注解的使用位置 方法
@Retention(RetentionPolicy.RUNTIME) //指明修饰的注解的生存周期 运行时
@Import(value = LogAspect.class)
public @interface EnableLogAspect {

}
