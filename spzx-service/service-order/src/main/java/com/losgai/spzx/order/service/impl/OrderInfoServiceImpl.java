package com.losgai.spzx.order.service.impl;

import com.losgai.spzx.common.exception.SpzxException;
import com.losgai.spzx.feign.cart.CartFeignClient;
import com.losgai.spzx.feign.product.ProductFeignClient;
import com.losgai.spzx.feign.user.UserFeignClient;
import com.losgai.spzx.model.dto.h5.OrderInfoDto;
import com.losgai.spzx.model.entity.h5.CartInfo;
import com.losgai.spzx.model.entity.order.OrderInfo;
import com.losgai.spzx.model.entity.order.OrderItem;
import com.losgai.spzx.model.entity.order.OrderLog;
import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.model.entity.user.UserAddress;
import com.losgai.spzx.model.entity.user.UserInfo;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.h5.TradeVo;
import com.losgai.spzx.order.mapper.OrderInfoMapper;
import com.losgai.spzx.order.mapper.OrderItemMapper;
import com.losgai.spzx.order.mapper.OrderLogMapper;
import com.losgai.spzx.order.service.OrderInfoService;
import com.losgai.spzx.utils.AuthContextUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public TradeVo getTrade() {
        //获取购物车中所有选中的商品列表
        List<CartInfo> cartInfoList = cartFeignClient.getCartCheckedList();

        TradeVo tradeVo = new TradeVo();
        //封装订单集合
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList){
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        }
        tradeVo.setOrderItemList(orderItemList);

        BigDecimal totalAmount = new BigDecimal(0);
        //计算总金额
        for (OrderItem orderItem : orderItemList){
            totalAmount = totalAmount.add(orderItem
                    .getSkuPrice()
                    .multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        tradeVo.setTotalAmount(totalAmount);

        return tradeVo;
    }

    @Override
    @Transactional
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        //1 OrderInfoDto里面获取所有订单项的集合
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();

        //2 判断是否为空
        if(CollectionUtils.isEmpty(orderItemList)){
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }

        //3 不为空，校验库存
        for(OrderItem orderItem : orderItemList){
            //根据skuId获取sku信息
            ProductSku productSku = productFeignClient.getSkuInfo(orderItem.getSkuId());
            if(productSku==null){
                throw new SpzxException(ResultCodeEnum.DATA_ERROR);
            }

            //校验库存量是否充足
            if(productSku.getStockNum() < orderItem.getSkuNum()){
                throw new SpzxException(ResultCodeEnum.STOCK_LESS);
            }
        }

        //4 库存充足，创建订单
        OrderInfo orderInfo = new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        // TODO 远程调用service-user模块的用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddressById(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);

        //5 添加数据至order_info表
        orderInfoMapper.save(orderInfo);

        //6 添加数据至order_item表
        for(OrderItem orderItem : orderItemList){
            //设置对应订单id
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }

        //7 更新order_log日志
        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        //8 远程调用选中的订单商品，从购物车移除
        cartFeignClient.deleteChecked();

        //9 返回订单id
        return orderInfo.getId();
    }

    //获取订单信息
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getById(orderId);
    }

    //立即购买
    @Override
    public TradeVo buy(Long skuId) {
        //封装订单项目集合
        List<OrderItem> orderItemList = new ArrayList<>();
        ProductSku skuInfo = productFeignClient.getSkuInfo(skuId);
        OrderItem orderItem = new OrderItem();
        //设置对应的5个数据
        orderItem.setSkuId(skuInfo.getId());
        orderItem.setSkuNum(1);
        orderItem.setSkuName(skuInfo.getSkuName());
        orderItem.setSkuPrice(skuInfo.getSalePrice());
        orderItem.setThumbImg(skuInfo.getThumbImg());
        orderItemList.add(orderItem);

        TradeVo tradeVo = new TradeVo();

        tradeVo.setTotalAmount(skuInfo.getSalePrice());
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;
    }
}
