package com.losgai.spzx.user.service;

import com.losgai.spzx.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    UserAddress getUserAddressById(Long id);
}
