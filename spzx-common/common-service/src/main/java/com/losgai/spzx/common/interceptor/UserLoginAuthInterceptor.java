package com.losgai.spzx.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.losgai.spzx.model.entity.user.UserInfo;
import com.losgai.spzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                      HttpServletResponse response,
                      Object handler) throws Exception {
        //Redis取用户信息，取到ThreadLoacal里面
        String token = request.getHeader("token");
        String userJson = redisTemplate.opsForValue().get("user_spzx: " + token);
        //放到ThreadLocal里面
        AuthContextUtil.setUserInfo(JSON.parseObject(userJson, UserInfo.class));
        return true;
    }
}
