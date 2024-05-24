package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku pSku);

    List<ProductSku> findProductSkuById(Long productId);

    void updateById(ProductSku pSku);

    void deleteByProductId(Long id);
}
