package com.losgai.spzx.user.service;

import com.losgai.spzx.model.dto.h5.UserLoginDto;
import com.losgai.spzx.model.dto.h5.UserRegisterDto;
import com.losgai.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto registerDto);

    String login(UserLoginDto loginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
