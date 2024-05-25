package com.losgai.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.losgai.spzx.manager.mapper.OrderInfoMapper;
import com.losgai.spzx.manager.mapper.OrderStatisticsMapper;
import com.losgai.spzx.manager.service.OrderInfoService;
import com.losgai.spzx.model.dto.order.OrderStatisticsDto;
import com.losgai.spzx.model.entity.order.OrderStatistics;
import com.losgai.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    @Override
    public OrderStatisticsVo getOrderStatistics(OrderStatisticsDto orderStatisticsDto) {
        //1 根据Dto条件返回统计数据
        List<OrderStatistics> orderStatisticsList
                = orderStatisticsMapper.selectList(orderStatisticsDto);

        //2 遍历List集合，得到所有日期，把所有日期封装返回到新的list集合里面
        List<String> dateList = orderStatisticsList.stream(). //流处理
                map(orderStatistics ->
                        DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .collect(Collectors.toList()); //可以替换成toList()试试

        //3 遍历List集合，得到所有日期对应总金额，把所有金额封装返回到新的list集合里面
        List<BigDecimal> decimalList = orderStatisticsList.stream(). //流处理
                        map(OrderStatistics::getTotalAmount)
                        .collect(Collectors.toList());

        //4 把2个list集合封装至Vo中
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setAmountList(decimalList);
        orderStatisticsVo.setDateList(dateList);
        return orderStatisticsVo;
    }
}
