package com.losgai.spzx.manager.controller;

import com.losgai.spzx.manager.service.OrderInfoService;
import com.losgai.spzx.model.dto.order.OrderStatisticsDto;
import com.losgai.spzx.model.entity.order.OrderStatistics;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    // 订单统计
    @GetMapping("/getOrderStatisticsData")
    public Result getOrderStatistics(OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo =  //返回Vo封装数据
                orderInfoService.getOrderStatistics(orderStatisticsDto);
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }
}
