package com.losgai.spzx.order.mapper;

import com.losgai.spzx.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {

    void save(OrderInfo orderInfo);

    OrderInfo getById(Long orderId);
}
