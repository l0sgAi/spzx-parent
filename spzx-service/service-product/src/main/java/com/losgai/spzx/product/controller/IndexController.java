package com.losgai.spzx.product.controller;

import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.h5.IndexVo;
import com.losgai.spzx.product.service.CategoryService;
import com.losgai.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product/index")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result index() { //主页
        //1.所有一级分类
        List<Category> categoryList = categoryService.findFirstCategoryListByParentId();

        //2.根据销量排序，获取前10条记录
        List<ProductSku> productSkuList = productService.findProductSkuBySal();

        //3.封装数据至VO对象
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }


}
