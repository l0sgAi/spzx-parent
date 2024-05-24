package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    void save(ProductDetails productDetails);

    ProductDetails findProductDetailsById(Long productId);

    void updateById(ProductDetails productDetails);


    void deleteByProductId(Long id);
}
