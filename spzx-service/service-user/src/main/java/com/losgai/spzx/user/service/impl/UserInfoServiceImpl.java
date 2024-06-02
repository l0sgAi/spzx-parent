package com.losgai.spzx.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.losgai.spzx.common.exception.SpzxException;
import com.losgai.spzx.model.dto.h5.UserLoginDto;
import com.losgai.spzx.model.dto.h5.UserRegisterDto;
import com.losgai.spzx.model.entity.user.UserInfo;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.h5.UserInfoVo;
import com.losgai.spzx.user.mapper.UserInfoMapper;
import com.losgai.spzx.user.service.UserInfoService;
import com.losgai.spzx.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public void register(UserRegisterDto registerDto) { //注册
        //1 Dto 获取相关信息
        String username = registerDto.getUsername();
        String nickName = registerDto.getNickName();
        String password = registerDto.getPassword();
        String code = registerDto.getCode();

        //2 验证码校验 从redis获取发送验证码 进行比对
        String codeValidate = redisTemplate.opsForValue().get("phone_code: " + username); //这里userName就是手机号
        if (codeValidate != null && !codeValidate.equals(code)) { //不一致，验证失败
            throw new SpzxException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        UserInfo userInfo = userInfoMapper.selectByUserName(username);
        //4 校验用户名不能重复
        if (userInfo != null) {
            throw new SpzxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //5 封装数据 调用方法添加至数据库
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPhone(username);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);


        //6 redis删除发送的验证码
        redisTemplate.delete("phone_code: " + username);

    }

    @Override
    public String login(UserLoginDto loginDto) {
        //1 Dto 获取用户名和密码
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        //2 查询数据库校验用户名密码
        UserInfo userInfo = userInfoMapper.selectByUserName(username);
        String dataBasePassword = userInfo.getPassword();
        String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!dataBasePassword.equals(md5Pwd)) { //和数据库密码不相同
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR);
        }

        //3 校验用户状态
        if (userInfo.getStatus() == 0) { //用户被禁用
            throw new SpzxException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //4 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //5 存入redis
        redisTemplate.opsForValue().set("user_spzx: " + token, JSON.toJSONString(userInfo),
                14, TimeUnit.DAYS); //设置14天过期

        //6 返回token
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        //1 redis根据token获取信息
//        String userJsonString = redisTemplate.opsForValue().get("user_spzx: " + token);
//        if(!StringUtils.hasText(userJsonString)){ //为空，返回未登录异常
//            throw new SpzxException(ResultCodeEnum.LOGIN_AUTH);
//        }
//
//        UserInfo userInfo = JSON.parseObject(userJsonString, UserInfo.class); //转回UserInfo对象

        //从ThreadLocal获取用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);

        return userInfoVo;
    }
}
