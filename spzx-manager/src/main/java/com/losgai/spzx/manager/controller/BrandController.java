package com.losgai.spzx.manager.controller;


import com.github.pagehelper.PageInfo;
import com.losgai.spzx.manager.service.BrandService;
import com.losgai.spzx.model.entity.product.Brand;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/{page}/{limit}") //分页列表显示(查询)
    public Result findPageList(@PathVariable(name = "page") Integer page,
                               @PathVariable(name = "limit") Integer limit) {
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save") //新增品牌
    public Result save(@RequestBody Brand brand) {
        brandService.saveBrand(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //TODO 修改和删除部分

    //品牌修改方法
    @PutMapping ("/updateBrand")
    public Result updateBrand(@RequestBody Brand brand) {
        brandService.updateBrand(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //品牌删除方法
    @DeleteMapping("/deleteBrandById/{brandId}")
    public Result deleteBrandById(@PathVariable("brandId") Long brandId) {
        brandService.deleteBrandById(brandId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查询所有品牌列表
    @GetMapping("/findAllBrands")
    public Result findAllBrands() {
       List<Brand> brandList = brandService.findAllBrands();
       return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }

}
