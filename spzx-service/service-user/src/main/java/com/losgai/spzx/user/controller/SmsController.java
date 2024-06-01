package com.losgai.spzx.user.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.spzx.model.dto.h5.ProductSkuDto;
import com.losgai.spzx.model.entity.product.ProductSku;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.h5.ProductItemVo;
import com.losgai.spzx.user.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone) {
        smsService.sendCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
