package com.losgai.spzx.order.service;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.h5.OrderInfoDto;
import com.losgai.spzx.model.entity.order.OrderInfo;
import com.losgai.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findOrderByPage(Integer page, Integer limit, Integer orderStatus);
}
