package com.losgai.spzx.product.service;

import com.losgai.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findFirstCategoryListByParentId();

    List<Category> findCategoryTree();
}
