package com.losgai.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.ProductSpecService;
import com.losgai.spzx.model.entity.product.ProductSpec;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/{page}/{limit}") //分页列表
    public Result list(@PathVariable("page") Integer page,
                       @PathVariable("limit") Integer limit) {
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "商品规格管理:添加", businessType = 1,operatorType = OperatorType.MANAGE)
    @PostMapping("/save") //添加方法
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "商品规格管理:修改", businessType = 2,operatorType = OperatorType.MANAGE)
    @PutMapping("/updateById") //修改方法
    public Result updateById(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "商品规格管理:删除", businessType = 3,operatorType = OperatorType.MANAGE)
    @DeleteMapping("/deleteById/{id}") //删除接口
        public Result deleteById(@PathVariable("id") Long id) {
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll") //查询所有
    public Result findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
