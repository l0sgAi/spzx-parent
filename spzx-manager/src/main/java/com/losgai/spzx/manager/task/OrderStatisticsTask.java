package com.losgai.spzx.manager.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.losgai.spzx.manager.mapper.OrderInfoMapper;
import com.losgai.spzx.manager.mapper.OrderStatisticsMapper;
import com.losgai.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class OrderStatisticsTask {
    //TODO 凌晨2点 0 0 2 * * ?

    //测试定时任务
    //每5s执行一次方法
   /* @Scheduled(cron =" 0/5 * * * * ? ")
    public void HelloTask(){
        System.out.println("====================\n每五秒执行:");
        System.out.println(new Date().toInstant());
    }*/
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    //每天凌晨2点统计查询前一天的数据，把统计后的数据添加到统计结果表里面
//    @Scheduled(cron = " 0/10 * * * * ?") //TODO 测试
    @Scheduled(cron = " 0 0 2 * * ?")
    @Transactional
    public void orderTotalStatistics(){
        //1.获取前一天的日期
        String createDate =
                DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");

        //2.根据前一天日期分组统计
        OrderStatistics orderStatistics =
                orderInfoMapper.selectStatisticsByDate(createDate);

        //3.统计后的数据添加到结果的表里面
        if(orderStatistics != null){
            orderStatisticsMapper.insertOrderStatistics(orderStatistics);
        }
    }
}
