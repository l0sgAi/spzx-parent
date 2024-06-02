package com.losgai.spzx.user.mapper;

import com.losgai.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

    UserInfo selectByUserName(String userName);

    void save(UserInfo userInfo);
}
