package com.losgai.spzx.common.annotation;

import com.losgai.spzx.common.config.UserWebMvcConfig;
import com.losgai.spzx.common.feign.UserTokenOpenFeignInterceptor;
import com.losgai.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = { UserTokenOpenFeignInterceptor.class})
public @interface EnableTokenFeignInterceptor {

}
