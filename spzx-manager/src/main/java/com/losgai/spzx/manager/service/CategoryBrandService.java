package com.losgai.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.product.CategoryBrandDto;
import com.losgai.spzx.model.entity.product.CategoryBrand;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateCategoryBrand(CategoryBrand categoryBrand);

    void deleteCategoryBrand(Long id);
}
