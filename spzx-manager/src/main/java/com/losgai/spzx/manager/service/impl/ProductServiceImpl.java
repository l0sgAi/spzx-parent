package com.losgai.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.manager.mapper.ProductDetailsMapper;
import com.losgai.spzx.manager.mapper.ProductMapper;
import com.losgai.spzx.manager.mapper.ProductSkuMapper;
import com.losgai.spzx.manager.service.ProductService;
import com.losgai.spzx.model.dto.product.ProductDto;
import com.losgai.spzx.model.entity.product.Product;
import com.losgai.spzx.model.entity.product.ProductDetails;
import com.losgai.spzx.model.entity.product.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list = productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public void save(Product product) {
        //1.获取商品基本信息 product表
        product.setStatus(0); //设置为初始状态 0
        product.setAuditStatus(0); //设置审核状态 0 待审核
        productMapper.save(product);

        //2.获取商品sku表集合,保存sku信息 product_sku表
        List<ProductSku> productSkuList = product.getProductSkuList();
        int i = 1;
        for (ProductSku productSku : productSkuList) {
            ProductSku pSku = productSku;
            //设置商品sku编号
            pSku.setSkuCode(product.getId() + "_" + i);
            i++;
            //设置商品id
            pSku.setProductId(product.getId());
            //设置Sku名称
            pSku.setSkuName(product.getName() + " " + pSku.getSkuSpec());
            pSku.setSaleNum(0); //设置销量
            pSku.setStatus(0); //设置状态
            productSkuMapper.save(pSku); //遍历保存sku信息
        }

        //3.保存商品详情数据 product_details表
        ProductDetails productDetails =new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }

    @Override
    @Transactional
    public Product getProductById(Long productId) {
        //1.根据id查询商品基本信息
        Product product = productMapper.findProductById(productId);

        //2.根据id查询商品sku信息列表
        List<ProductSku> productSkuList = productSkuMapper.findProductSkuById(productId);
        product.setProductSkuList(productSkuList); //传入集合

        //3.根据id查询商品详情信息
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(productId);
        String imageUrls = productDetails.getImageUrls();
        productDetails.setImageUrls(imageUrls);

        return product;
    }

    @Override
    @Transactional
    public void updateById(Product product) {
        //修改product表
        productMapper.updateById(product);

        //修改product_sku表
        List<ProductSku> list = product.getProductSkuList();
        list.forEach(pSku -> {
            productSkuMapper.updateById(pSku);
        });

        //修改product_details表
        //获取现有图片url
        String detailsImageUrls = product.getDetailsImageUrls();
        //根据id获取当前的商品详情信息
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(product.getId());
        //更新图片url
        productDetails.setImageUrls(detailsImageUrls);
        //更新product_details表
        productDetailsMapper.updateById(productDetails);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        //删除product表数据
        productMapper.deleteById(id);

        //删除product_sku表数据
        productSkuMapper.deleteByProductId(id);

        //删除product_details表数据
        productDetailsMapper.deleteByProductId(id);

    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);

        if(auditStatus == 1){
            product.setAuditStatus(auditStatus);
            product.setAuditMessage("审批通过");
        }
        else{
            product.setAuditStatus(auditStatus);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1){
            product.setStatus(1); //上架
        }else {
            product.setStatus(-1); //自主下架
        }
        productMapper.updateById(product);
    }
}
