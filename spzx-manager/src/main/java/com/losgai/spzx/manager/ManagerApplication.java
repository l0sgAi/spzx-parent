package com.losgai.spzx.manager;

import com.losgai.spzx.manager.properties.MinioProperties;
import com.losgai.spzx.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.losgai.spzx"})
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
@EnableScheduling //开启定时任务功能
public class ManagerApplication { //manager启动类
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
