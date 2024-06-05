package com.losgai.spzx.user.service.impl;

import com.losgai.spzx.model.entity.user.UserAddress;
import com.losgai.spzx.user.mapper.UserAddressMapper;
import com.losgai.spzx.user.service.UserAddressService;
import com.losgai.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    //获取用户地址列表
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findUserAddressList(userId);
    }

    @Override
    public UserAddress getUserAddressById(Long id) {
        return userAddressMapper.findUserAddressById(id);
    }
}
