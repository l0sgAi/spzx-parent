package com.losgai.spzx.feign.user;

import com.losgai.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-user")
public interface UserFeignClient {
    @GetMapping("api/user/userAddress/getUserAddress/{id}")
    public UserAddress getUserAddressById(@PathVariable("id") Long id);
}
