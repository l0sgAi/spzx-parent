package com.losgai.spzx.feign.product;

import com.losgai.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product") //远程调用接口
public interface ProductFeignClient {

    @GetMapping("/api/product/getBySkuId/{skuId}")
    public ProductSku getSkuInfo(@PathVariable("skuId") Long skuId);

}
