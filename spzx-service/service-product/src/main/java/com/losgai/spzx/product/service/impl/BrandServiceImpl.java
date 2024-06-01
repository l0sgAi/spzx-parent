package com.losgai.spzx.product.service.impl;

import com.losgai.spzx.model.entity.product.Brand;
import com.losgai.spzx.product.mapper.BrandMapper;
import com.losgai.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
