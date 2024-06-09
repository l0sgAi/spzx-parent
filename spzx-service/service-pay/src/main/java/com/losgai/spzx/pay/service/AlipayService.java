package com.losgai.spzx.pay.service;

import com.alipay.api.AlipayApiException;

public interface AlipayService {
    String submitAlipay(String orderNo);
}
