package com.losgai.spzx.manager.service;

import com.losgai.spzx.model.dto.order.OrderStatisticsDto;
import com.losgai.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatistics(OrderStatisticsDto orderStatisticsDto);
}
