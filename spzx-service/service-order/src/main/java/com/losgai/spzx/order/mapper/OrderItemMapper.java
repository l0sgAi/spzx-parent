package com.losgai.spzx.order.mapper;

import com.losgai.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);
}
