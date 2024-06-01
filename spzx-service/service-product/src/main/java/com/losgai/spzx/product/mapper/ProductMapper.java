package com.losgai.spzx.product.mapper;

import com.losgai.spzx.model.entity.product.Brand;
import com.losgai.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product selectById(Long productId);
}
