package com.losgai.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.manager.mapper.CategoryBrandMapper;
import com.losgai.spzx.manager.service.CategoryBrandService;
import com.losgai.spzx.model.dto.product.CategoryBrandDto;
import com.losgai.spzx.model.entity.product.Brand;
import com.losgai.spzx.model.entity.product.CategoryBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page,
                                              Integer limit,
                                              CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page, limit);
        List<CategoryBrand> categoryBrandList = categoryBrandMapper.findByPage(categoryBrandDto);
        return new PageInfo<>(categoryBrandList);
    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public void updateCategoryBrand(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateCategoryBrand(categoryBrand);
    }

    @Override
    public void deleteCategoryBrand(Long id) {
        categoryBrandMapper.deleteCategoryBrand(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
