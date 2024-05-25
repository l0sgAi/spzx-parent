package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {

    //统计前一天的交易金额
    OrderStatistics selectStatisticsByDate(String createDate);
}
