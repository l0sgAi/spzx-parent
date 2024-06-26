package com.losgai.spzx.user;

import com.losgai.spzx.common.annotation.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.losgai.spzx"})
@EnableUserLoginAuthInterceptor
public class UserApplication {
    public static void main(String[] args) {
       SpringApplication.run(UserApplication.class, args);
    }
}
