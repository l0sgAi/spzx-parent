package com.losgai.spzx.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.common.log.annotation.Log;
import com.losgai.spzx.common.log.enums.OperatorType;
import com.losgai.spzx.manager.service.ProductService;
import com.losgai.spzx.model.dto.product.ProductDto;
import com.losgai.spzx.model.entity.product.Product;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //商品上下架
    @Log(title = "商品管理:上下架", businessType = 2,operatorType = OperatorType.MANAGE)
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status) {
        productService.updateStatus(id, status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //商品上架审核
    @Log(title = "商品管理:审核", businessType = 2,operatorType = OperatorType.MANAGE)
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id,@PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //删除
    @Log(title = "商品管理:删除", businessType = 3,operatorType = OperatorType.MANAGE)
    @DeleteMapping("/deleteById/{id}")
    public Result remove(@Parameter(name = "id", description = "商品id", required = true)
                         @PathVariable Long id) {
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //保存修改的数据
    @Log(title = "商品管理:信息修改", businessType = 2,operatorType = OperatorType.MANAGE)
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product) {
        productService.updateById(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //根据商品id查询商品信息
//    @Log(title = "商品管理:查询", businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping("/getProductById/{productId}")
    public Result getProductById(@PathVariable("productId") Long productId) {
        Product product = productService.getProductById(productId);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    //添加商品信息
    @Log(title = "商品管理:新增", businessType = 1,operatorType = OperatorType.MANAGE)
    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    //列表查询分页接口
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page,
                       @PathVariable("limit") Integer limit,
                       ProductDto productDto) {
        PageInfo<Product> pageInfo = productService.findByPage(page,limit,productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
