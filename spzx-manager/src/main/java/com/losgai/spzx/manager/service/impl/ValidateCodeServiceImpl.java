package com.losgai.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.losgai.spzx.manager.service.ValidateCodeService;
import com.losgai.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        //1.通过工具hutool生成图片验证码
        //int width, int height, int codeCount, int circleCount 宽，高，验证码个数，干扰线个数
        CircleCaptcha circleCaptcha = CaptchaUtil.
                createCircleCaptcha(120, 45, 4, 10);
        String codeValue=circleCaptcha.getCode(); //获取验证码值
        String imageBase64 = circleCaptcha.getImageBase64(); //返回图片(编码信息)

        //2.将验证码存入redis,设置redis的key: UUID value:验证码的值 并设置过期时间
        String key= UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set("user:validate"+key,
                codeValue,5, TimeUnit.MINUTES); //调整Redis的过期时间:5分钟

        //3.返回ValidateVo对象
        ValidateCodeVo validateCodeVo=new ValidateCodeVo();
        validateCodeVo.setCodeKey(key); //redis存储数据的key
        validateCodeVo.setCodeValue("data:image/png;base64,"+imageBase64);
        return validateCodeVo;
    }
}
