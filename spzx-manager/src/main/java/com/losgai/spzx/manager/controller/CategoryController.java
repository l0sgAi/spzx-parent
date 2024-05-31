package com.losgai.spzx.manager.controller;

import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.CategoryService;
import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //分类列表，每次只查询一层数据
    //SELECT * FROM category WHERE PARENT_ID = 0
    @GetMapping ("/findCategoryList/{parentId}")
    public Result findByParentId(@PathVariable(name = "parentId") Long parentId) {
        List<Category> list = categoryService.findByParentId(parentId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //导出Excel数据接口
    @Log(title = "导出分类Excel", businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        categoryService.exportExcel(response);
    }

    //导入Excel文件
    @Log(title = "导入分类Excel", businessType = 0,operatorType = OperatorType.MANAGE)
    @PostMapping("/importExcel")
    public Result importExcel(MultipartFile file) {
        //获取上传文件
        categoryService.importExcel(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
