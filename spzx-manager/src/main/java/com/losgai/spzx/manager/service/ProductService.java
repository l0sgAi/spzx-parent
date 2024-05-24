package com.losgai.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.product.ProductDto;
import com.losgai.spzx.model.entity.product.Product;

public interface ProductService {
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    void save(Product product);

    Product getProductById(Long productId);

    void updateById(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}
