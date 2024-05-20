package com.losgai.spzx.manager;

import com.losgai.spzx.manager.properties.MinioProperties;
import com.losgai.spzx.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.losgai.spzx"})
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
public class ManagerApplication { //manager启动类
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
