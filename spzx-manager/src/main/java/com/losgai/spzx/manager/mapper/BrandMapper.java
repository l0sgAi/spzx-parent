package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    void save(Brand brand);

    List<Brand> findByPage();

    void updateBrand(Brand brand);

    void deleteBrandById(Long brandId);

}
