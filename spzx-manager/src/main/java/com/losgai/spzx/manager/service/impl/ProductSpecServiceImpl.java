package com.losgai.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.manager.mapper.ProductSpecMapper;
import com.losgai.spzx.manager.service.ProductSpecService;
import com.losgai.spzx.model.entity.product.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> list = productSpecMapper.findByPage();
        return new PageInfo<>(list);
    }

    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }
}
