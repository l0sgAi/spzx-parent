package com.losgai.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.losgai.spzx.cart.service.CartService;
import com.losgai.spzx.feign.product.ProductFeignClient;
import com.losgai.spzx.model.entity.h5.CartInfo;
import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void addToCart(Long skuId, Integer skuNum) {

        //1 必须登录状态，获取用户ID
        //从ThreadLocal里面获取用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        //构建购物车hash Key
        String cartKey = this.getCartKey(userId);

        //2 购物车Redis获取信息
        //根据用户ID和skuId获取购物车信息(Hash类型Key + Field)
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

        //3 如果购物车已经存在商品，添加数量
        CartInfo cartInfo = null; //购物车商品空对象
        if (cartInfoObj != null) { //购物车商品已经存在
            //cartInfoObj转cartInfo
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            //数量相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            //设置属性 1为选中状态
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            //4 没有对应商品，添加到购物车(存入Redis)
            //远程调用实现: nacos + openFeign实现 根据SkuId获取Sku信息
            cartInfo = new CartInfo();

            //TODO 远程调用实现: nacos + openFeign实现 根据SkuId获取Sku信息
            ProductSku productSku = productFeignClient.getSkuInfo(skuId);
            //设置相关数据到cartInfo里面
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());

        }
        //添加到Redis
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));

    }

    @Override
    public List<CartInfo> getCartList() {
        //1 获取用户信息获取CartKey
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 根据key取值封装列表返回
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);

        //流式类型转换 按时间排序
        if (!CollectionUtils.isEmpty(valueList)) {
            return valueList.stream().map(cartInfoObj ->
                            JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public void deleteCart(Long skuId) {
        //1 获取用户信息获取CartKey
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 根据sku删除
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(skuId));

    }

    //更新购物车商品选中状态
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        //1 构建查询redis里面的key值
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 判断key是否包含field
        Boolean hasKey = redisTemplate
                .opsForHash()
                .hasKey(cartKey, String.valueOf(skuId));

        //有field
        if (hasKey) {
            //3 根据key+field把value获取出来
            String cartString = redisTemplate
                    .opsForHash()
                    .get(cartKey, String.valueOf(skuId))
                    .toString();

            //4 更新选中状态
            CartInfo cartInfo = JSON.parseObject(cartString, CartInfo.class);
            cartInfo.setIsChecked(isChecked);

            //5 放回redis
            redisTemplate.opsForHash()
                    .put(cartKey, String.valueOf(skuId),
                            JSON.toJSONString(cartInfo));
        }
    }

    //更新全选状态
    @Override
    public void allCheckCart(Integer isChecked) {
        //1 构建查询redis里面的key值
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 获取购物车列表
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);
        List<CartInfo> cartInfoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(valueList)) {
            cartInfoList =
                    valueList.stream().map(cartInfoObj ->
                                    JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                            .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                            .collect(Collectors.toList());
        }

        //3 遍历购物车列表 更新选中状态
        cartInfoList.stream().forEach(cartInfo -> {
                    cartInfo.setIsChecked(isChecked);
                    redisTemplate.opsForHash().put(
                            cartKey,
                            String.valueOf(cartInfo.getSkuId()),
                            JSON.toJSONString(cartInfo));
                }
        );

    }

    @Override
    public void clearCart() {
        //1 构建查询redis里面的key值
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 根据key删除redis数据
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        //1 构建查询redis里面的key值
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 根据key查找redis数据
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(valueList)){
            return valueList.stream().map(cartInfoObj ->
                            JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1) //过滤器，只选择选中的
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteCheckedCart() {
        //1 构建查询redis里面的key值
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 根据key获取value
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(valueList)){
            valueList.stream().map(cartInfoObj ->
                            JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete
                            (cartKey, String.valueOf(cartInfo.getSkuId())));
        }

    }

    private String getCartKey(Long userId) { //返回对应的购物车Key
        return "user_cart: " + userId;
    }
}
