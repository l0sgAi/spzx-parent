package com.losgai.spzx.user.mapper;

import com.losgai.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    List<UserAddress> findUserAddressList(Long userId);

    UserAddress findUserAddressById(Long id);
}
