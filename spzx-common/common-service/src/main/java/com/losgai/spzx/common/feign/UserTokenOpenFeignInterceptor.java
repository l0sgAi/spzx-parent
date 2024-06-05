package com.losgai.spzx.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserTokenOpenFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();

        //拿到请求头，让远程调用也能获取用户信息
        if (requestAttributes != null) {
            String token = requestAttributes.getRequest().getHeader("token");
            requestTemplate.header("token",token);
        }

    }
}
