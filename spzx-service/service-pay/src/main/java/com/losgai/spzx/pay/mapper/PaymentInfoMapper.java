package com.losgai.spzx.pay.mapper;

import com.losgai.spzx.model.entity.pay.PaymentInfo;
import com.losgai.spzx.pay.service.PaymentInfoService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
public interface PaymentInfoMapper{
    PaymentInfo selectByOrderNo(String orderNo);

    void insert(PaymentInfo paymentInfo);

    void updatePaymentInfo(PaymentInfo paymentInfo);
}
