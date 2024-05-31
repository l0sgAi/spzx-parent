package com.losgai.spzx.product.service.impl;

import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.product.mapper.ProductSkuMapper;
import com.losgai.spzx.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSkuServiceImpl implements ProductSkuService {
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Override
    public List<ProductSku> findProductSkuBySal() {
        return productSkuMapper.findProductSkuBySal();
    }
}
