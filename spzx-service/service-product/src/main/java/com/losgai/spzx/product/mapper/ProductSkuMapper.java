package com.losgai.spzx.product.mapper;

import com.losgai.spzx.model.dto.h5.ProductSkuDto;
import com.losgai.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findProductSkuBySal(); //按销量排序查找前10的商品

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku selectById(Long skuId);

    List<ProductSku> findByProductId(Long productId);

    void updateSale(Long skuId, Integer num);
}
