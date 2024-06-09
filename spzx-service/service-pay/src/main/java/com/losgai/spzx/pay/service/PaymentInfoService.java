package com.losgai.spzx.pay.service;

import com.losgai.spzx.model.entity.pay.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService {
    //保存支付记录
    PaymentInfo savePaymentInfo(String orderNo);

    void updatePaymentStatus(Map<String, String> paramMap); //支付完成后更新支付记录
}
