package com.losgai.spzx.product.service;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.h5.ProductSkuDto;
import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.model.vo.h5.ProductItemVo;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySal();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo findProductItem(Long skuId);
}
