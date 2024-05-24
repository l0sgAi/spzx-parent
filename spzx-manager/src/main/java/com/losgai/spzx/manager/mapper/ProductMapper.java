package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.dto.product.ProductDto;
import com.losgai.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findByPage(ProductDto productDto); //分页多表查询
    void save(Product product);

    Product findProductById(Long productId);

    void updateById(Product product);

    void deleteById(Long id);
}
