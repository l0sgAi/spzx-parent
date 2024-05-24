package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.dto.product.CategoryBrandDto;
import com.losgai.spzx.model.entity.product.Brand;
import com.losgai.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateCategoryBrand(CategoryBrand categoryBrand);

    void deleteCategoryBrand(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
