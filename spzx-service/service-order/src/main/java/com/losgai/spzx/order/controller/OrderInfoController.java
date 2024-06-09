package com.losgai.spzx.order.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.h5.OrderInfoDto;
import com.losgai.spzx.model.entity.order.OrderInfo;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.h5.TradeVo;
import com.losgai.spzx.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理接口")
@RestController
@RequestMapping("/api/order/orderInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Operation(summary = "确认下单")
    @GetMapping("/auth/trade")
    public Result trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    //生成订单
    @Operation(summary = "生成订单")
    @PostMapping ("/auth/submitOrder")
    public Result sunmitOrder(@RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单信息")
    @GetMapping ("/auth/{orderId}")
    public Result getOrderInfo(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "立即购买")
    @GetMapping ("/auth/buy/{skuId}")
    public Result buy(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/{page}/{limit}")
    public Result updateOrderStatus(@PathVariable Integer page,
                                    @PathVariable Integer limit,
                                    @RequestParam(required = false) Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findOrderByPage(page, limit, orderStatus);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    //远程调用：根据订单编号获取订单信息
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable String orderNo) {
        System.out.println("\n***订单的orderNo: \n" + orderNo);
        return orderInfoService.getByOrderNo(orderNo);
    }

    //远程调用：更新订单状态
    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo , @PathVariable(value = "orderStatus") Integer orderStatus) {
        orderInfoService.updateOrderStatus(orderNo , orderStatus);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
