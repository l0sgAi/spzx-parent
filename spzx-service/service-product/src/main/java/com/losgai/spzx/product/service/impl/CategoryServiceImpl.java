package com.losgai.spzx.product.service.impl;

import com.alibaba.fastjson2.JSON;
import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.product.mapper.CategoryMapper;
import com.losgai.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //category:one
    @Override
    public List<Category> findFirstCategoryListByParentId() {
        //1 先查询Redis查询是否有所有一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");

        //2 如果有，直接返回
        if (StringUtils.hasText(categoryOneJson)) {
            //categoryOneJson字符串转换成List<Category>集合
            return JSON.parseArray(categoryOneJson, Category.class);
        }

        //3 没有所有一级分类，查询数据库并封装到Redis
        List<Category> categoryList = categoryMapper.selectListByParentId();
        //查询内容放Redis 设置7天过期
        redisTemplate.opsForValue().set
                ("category:one",
                JSON.toJSONString(categoryList),
                        7,
                        TimeUnit.DAYS);

        return categoryMapper.selectListByParentId();
    }

    //查询所有分类，树型封装
    @Override
    @Cacheable(value = "category",key = "'all'")  //category::all 快捷缓存设置 效果同上
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
