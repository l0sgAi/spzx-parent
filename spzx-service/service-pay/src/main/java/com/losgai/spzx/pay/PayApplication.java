package com.losgai.spzx.pay;

import com.losgai.spzx.common.annotation.EnableUserLoginAuthInterceptor;
import com.losgai.spzx.pay.utils.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {"com.losgai.spzx"})
@EnableConfigurationProperties(value = { AlipayProperties.class })
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
