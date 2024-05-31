package com.losgai.spzx.product.service.impl;

import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.product.mapper.CategoryMapper;
import com.losgai.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findFirstCategoryListByParentId() {
        return categoryMapper.selectListByParentId();
    }

    //查询所有分类，树型封装
    @Override
    public List<Category> findCategoryTree() {
        //1 查询所有分类
        List<Category> allCategoryList = categoryMapper.findAllCategoryList();

        //2 遍历所有分类的list集合，得到所有一级分类(ParentId() == 0)
        List<Category> firstCategoryList = allCategoryList.stream().
                filter(category -> category.getParentId() == 0).
                collect(Collectors.toList());

        //3 遍历一级分类的所有集合，得到二级分类
        firstCategoryList.forEach(firstCategory -> {
            List<Category> secondCategoryList =
                    allCategoryList.stream() //在所有集合中查找parentId等于当前一级分类的id的集合
                            .filter(category -> Objects.equals(category.getParentId(), firstCategory.getId()))
                            .collect(Collectors.toList());
            firstCategory.setChildren(secondCategoryList);
            //4 遍历二级分类的所有集合，得到三级分类
            secondCategoryList.forEach(secondCategory -> {
                List<Category> thirdCategoryList =
                        allCategoryList.stream()
                                .filter(category -> Objects.equals(category.getParentId(), secondCategory.getId()))
                                .collect(Collectors.toList());
                secondCategory.setChildren(thirdCategoryList);
            });
        });

        return firstCategoryList;
    }
}
