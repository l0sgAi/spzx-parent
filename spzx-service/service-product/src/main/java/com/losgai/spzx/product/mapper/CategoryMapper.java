package com.losgai.spzx.product.mapper;

import com.losgai.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectListByParentId(); //查询所有一级菜单

    List<Category> findAllCategoryList();
}
