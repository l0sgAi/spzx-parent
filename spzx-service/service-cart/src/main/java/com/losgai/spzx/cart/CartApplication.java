package com.losgai.spzx.cart;

import com.losgai.spzx.common.annotation.EnableUserLoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //排除数据库自动化配置，因为只用Redis缓存
@EnableFeignClients(basePackages = {"com.losgai.spzx"})
@EnableUserLoginInterceptor
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class,args);
    }
}
