package com.losgai.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserProperties { //设置不拦截的Url
    private List<String> noAuthUrls;
}
