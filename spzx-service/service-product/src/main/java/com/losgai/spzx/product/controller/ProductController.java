package com.losgai.spzx.product.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.h5.ProductSkuDto;
import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.h5.ProductItemVo;
import com.losgai.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "分页查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit,
                       ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> pageInfo =
                productService.findByPage(page, limit, productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);

    }

    //查询商品详情
    @Operation(summary = "商品详情")
    @GetMapping("/item/{skuId}")
    public Result item(@PathVariable Long skuId) {
        ProductItemVo productItemVo = productService.findProductItem(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

    //远程调用，根据skuId返回skuInfo
    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getSkuInfo(@PathVariable Long skuId) {
        ProductSku productSku = productService.findProductSkuById(skuId);
        System.out.println("\n*******远程调用，根据skuId返回skuInfo********\n" + productSku);
        return productSku;
    }

}
