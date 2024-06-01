package com.losgai.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.h5.ProductSkuDto;
import com.losgai.spzx.model.entity.product.Product;
import com.losgai.spzx.model.entity.product.ProductDetails;
import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.model.vo.h5.ProductItemVo;
import com.losgai.spzx.product.mapper.ProductDetailsMapper;
import com.losgai.spzx.product.mapper.ProductMapper;
import com.losgai.spzx.product.mapper.ProductSkuMapper;
import com.losgai.spzx.product.service.ProductService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public List<ProductSku> findProductSkuBySal() {
        return productSkuMapper.findProductSkuBySal();
    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);

        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);

        return new PageInfo<>(productSkuList);
    }

    @Override
    @Transactional
    public ProductItemVo findProductItem(Long skuId) {
        //1 创建对象，用于封装数据
        ProductItemVo productItemVo = new ProductItemVo();

        //2 根据id获取sku信息
        ProductSku productSku = productSkuMapper.selectById(skuId);

        //3 根据sku获取对应的productId商品信息
        Long productId = productSku.getProductId();
        Product product = productMapper.selectById(productId);

        //4 productId 获取详情信息
        ProductDetails productDetails = productDetailsMapper.selectByProductId(productId);

        //5 封装map集合
        Map<String, Object> skuSpecValueMap = new HashMap<>();
        //根据商品id获取所有sku列表
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productId);
        //遍历集合，把每一个sku的规格信息封装到map集合中
        productSkuList.forEach(item -> {
           skuSpecValueMap.put(item.getSkuSpec(),item.getId());
        });

        //6 把需要的数据封装到productItemVo对象中
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        //封装详情图片集合
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));

        //封装轮播图集合
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));

        //封装规格信息 fastjson1 不要用2
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));

        return productItemVo;
    }
}
