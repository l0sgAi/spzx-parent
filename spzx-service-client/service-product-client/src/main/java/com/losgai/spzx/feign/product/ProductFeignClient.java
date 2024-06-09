package com.losgai.spzx.feign.product;

import com.losgai.spzx.model.dto.product.SkuSaleDto;
import com.losgai.spzx.model.entity.order.OrderInfo;
import com.losgai.spzx.model.entity.product.ProductSku;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product") //远程调用接口
public interface ProductFeignClient {

    @GetMapping("/api/product/getBySkuId/{skuId}")
    public ProductSku getSkuInfo(@PathVariable("skuId") Long skuId);

    //更新商品库存和销量
    @PostMapping("/api/product/updateSkuSaleNum")
    Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList);

}
