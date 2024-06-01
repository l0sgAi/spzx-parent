package com.losgai.spzx.product.mapper;

import com.losgai.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {

    ProductDetails selectByProductId(Long productId);
}
