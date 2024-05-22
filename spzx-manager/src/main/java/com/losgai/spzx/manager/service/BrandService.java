package com.losgai.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.entity.product.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void saveBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteBrandById(Long brandId);

    List<Brand> findAllBrands();
}
