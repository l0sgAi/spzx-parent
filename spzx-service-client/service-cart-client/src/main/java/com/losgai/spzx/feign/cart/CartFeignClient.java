package com.losgai.spzx.feign.cart;

import com.losgai.spzx.model.entity.h5.CartInfo;
import com.losgai.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "service-cart")
public interface CartFeignClient {
    @GetMapping("api/order/cart/auth/getAllChecked")
    public List<CartInfo> getCartCheckedList();

    @GetMapping("api/order/cart/auth/deleteChecked")
    public Result deleteChecked();
}
