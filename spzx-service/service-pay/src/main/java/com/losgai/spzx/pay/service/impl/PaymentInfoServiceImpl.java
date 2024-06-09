package com.losgai.spzx.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.losgai.spzx.feign.order.OrderFeignClient;
import com.losgai.spzx.feign.product.ProductFeignClient;
import com.losgai.spzx.model.dto.product.SkuSaleDto;
import com.losgai.spzx.model.entity.order.OrderInfo;
import com.losgai.spzx.model.entity.order.OrderItem;
import com.losgai.spzx.model.entity.pay.PaymentInfo;
import com.losgai.spzx.pay.mapper.PaymentInfoMapper;
import com.losgai.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    @Transactional
    public PaymentInfo savePaymentInfo(String orderNo) {
        //1 根据订单编号获取支付记录
        PaymentInfo paymentInfo = paymentInfoMapper.selectByOrderNo(orderNo);

        //2 判断支付记录是否存在
        if (paymentInfo == null) {
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            //orderInfo中的数据封装到paymentInfo中
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);

            //添加
            paymentInfoMapper.insert(paymentInfo);
        }
        return paymentInfo;
    }

    @Override
    public void updatePaymentStatus(Map<String, String> map) {
        // 查询PaymentInfo
        PaymentInfo paymentInfo = paymentInfoMapper.selectByOrderNo(map.get("out_trade_no"));
        //支付已完成，不需要更新信息
        if (paymentInfo.getPaymentStatus() == 1) {
            return;
        }

        //更新支付信息
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updatePaymentInfo(paymentInfo);

        // 3、更新订单的支付状态
        orderFeignClient.updateOrderStatus(paymentInfo.getOrderNo(),1) ;

        // 4、更新商品销量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo());
        List<SkuSaleDto> skuSaleDtoList = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleDto skuSaleDto = new SkuSaleDto();
            skuSaleDto.setSkuId(item.getSkuId());
            skuSaleDto.setNum(item.getSkuNum());
            return skuSaleDto;
        }).collect(Collectors.toList());
        System.out.println(skuSaleDtoList);

        productFeignClient.updateSkuSaleNum(skuSaleDtoList) ;
    }
}
