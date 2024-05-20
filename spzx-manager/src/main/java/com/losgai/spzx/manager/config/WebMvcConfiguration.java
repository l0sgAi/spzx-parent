package com.losgai.spzx.manager.config;

import com.losgai.spzx.manager.interceptor.LoginAuthInterceptor;
import com.losgai.spzx.manager.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;
    //拦截器注册方法

    @Autowired
    private UserProperties userProperties;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                        /*.excludePathPatterns
                        ("/admin/system/index/login","/admin/system/index/generateValidateCode")*/
                .excludePathPatterns(userProperties.getNoAuthUrls())
                .addPathPatterns("/**"); //除了登录和验证码，其他都需要拦截
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //添加路径规则
                .allowCredentials(true) //是否允许跨域传递Cookie
                .allowedOriginPatterns("*") //允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
