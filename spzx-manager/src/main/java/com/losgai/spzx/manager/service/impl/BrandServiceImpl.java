package com.losgai.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.manager.mapper.BrandMapper;
import com.losgai.spzx.manager.service.BrandService;
import com.losgai.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) { //分页查询
        PageHelper.startPage(page, limit);
        List<Brand> pageInfoList = brandMapper.findByPage();
        return new PageInfo<>(pageInfoList);
    }

    @Override
    public void saveBrand(Brand brand) { //添加
        brandMapper.save(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public void deleteBrandById(Long brandId) {
        brandMapper.deleteBrandById(brandId);
    }

    @Override
    public List<Brand> findAllBrands() {
        ///查询所有品牌
        return brandMapper.findByPage();
    }
}
