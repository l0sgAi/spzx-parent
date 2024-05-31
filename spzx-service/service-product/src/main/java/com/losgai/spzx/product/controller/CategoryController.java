package com.losgai.spzx.product.controller;

import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查询所有分类并进行树型封装
    @GetMapping ("/findCategoryTree")
    public Result findFirstCategoryListByParentId() {
        List<Category> categoryList = categoryService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
