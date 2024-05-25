package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.dto.order.OrderStatisticsDto;
import com.losgai.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {
    //统计后的数据添加到结果的表里面
    void insertOrderStatistics(OrderStatistics orderStatistics);

    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}
