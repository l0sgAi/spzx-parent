package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {

    List<ProductSpec> findByPage();

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Long id);
}
