package com.losgai.spzx.product.mapper;

import com.losgai.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findProductSkuBySal(); //按销量排序查找前10的商品
}
