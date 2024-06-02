package com.losgai.spzx.user.controller;

import com.losgai.spzx.model.dto.h5.UserLoginDto;
import com.losgai.spzx.model.dto.h5.UserRegisterDto;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.h5.UserInfoVo;
import com.losgai.spzx.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/auth/getCurrentUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户登录")
    @PostMapping ("/login")
    public Result login(@RequestBody UserLoginDto loginDto){
        String token = userInfoService.login(loginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto registerDto){ //用户注册
        userInfoService.register(registerDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
