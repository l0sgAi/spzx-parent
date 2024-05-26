package com.losgai.spzx.manager.controller;


import com.github.pagehelper.PageInfo;
import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.CategoryBrandService;
import com.losgai.spzx.model.dto.product.CategoryBrandDto;
import com.losgai.spzx.model.entity.product.Brand;
import com.losgai.spzx.model.entity.product.CategoryBrand;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    //根据分类id查询品牌数据
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<Brand> list = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //分类品牌条件查询
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable("page") Integer page,
                             @PathVariable("limit") Integer limit,
                             CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> pageInfo =
                categoryBrandService.findByPage(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @Log(title = "分类品牌:添加", businessType = 1,operatorType = OperatorType.MANAGE)
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //TODO 修改和删除

    @Log(title = "分类品牌:修改", businessType = 1,operatorType = OperatorType.MANAGE)
    @PutMapping("/updateCategoryBrand")
    public Result updateCategoryBrand(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateCategoryBrand(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "分类品牌:删除", businessType = 1,operatorType = OperatorType.MANAGE)
    @DeleteMapping("/deleteCategoryBrand/{id}")
    public Result deleteCategoryBrand(@PathVariable("id") Long id) {
        categoryBrandService.deleteCategoryBrand(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
