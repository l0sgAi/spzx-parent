package com.losgai.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.spzx.common.exception.SpzxException;
import com.losgai.spzx.manager.mapper.SysRoleUserMapper;
import com.losgai.spzx.manager.mapper.SysUserMapper;
import com.losgai.spzx.manager.service.SysUserService;
import com.losgai.spzx.model.dto.system.AssginRoleDto;
import com.losgai.spzx.model.dto.system.LoginDto;
import com.losgai.spzx.model.dto.system.SysUserDto;
import com.losgai.spzx.model.entity.system.SysRoleUser;
import com.losgai.spzx.model.entity.system.SysUser;
import com.losgai.spzx.model.entity.user.UserInfo;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

   //用户登录方法
    @Override
    public LoginVo login(LoginDto loginDto) {
        //0.1 获取用户输入的验证码和存储的Redis key名称
        String captcha = loginDto.getCaptcha(); //输入的验证码
        String key = loginDto.getCodeKey(); //Redis key名称

        //0.2 根据Redis获取到的key,查询Redis存储的验证码(从Redis中根据Key取值)
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //0.3 比较用户输入的验证码和Redis存储的验证码是否一致
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(captcha, redisCode)){
            //判空或者验证码错误的情况(比较不分大小写)
            //如果不一致，登录失败,返回错误信息
            throw new SpzxException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //0.4 如果一致，登录成功，删除Redis中的验证码
        redisTemplate.delete("user:validate" + key);

        //1.从LoginDto中获取用户名
        String username = loginDto.getUserName();

        //2.根据用户名查询数据库表sys_user
        SysUser sysUser=sysUserMapper.selectUserInfoByUserName(username);

        //3.如果根据用户名查询不到，用户不存在，返回null
        if(sysUser==null){
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4.如果查询到用户信息，则用户存在，比较输入密码和数据库密码是否一致

        //5.密码一致，登录成功
        String db_password=sysUser.getPassword();
        //将输入密码MD5加密，再比对 (建议用sha256,MD5不安全)
        String input_password=DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!db_password.equals(input_password)){
            //密码不一致，登录失败
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR);
        }
        else {
            //密码一致，登录成功
            //生成唯一标识token
            //登录成功信息放入redis
            //返回loginVo对象
        }

        //6.密码不一致，登录失败

        //7.登录成功，生成唯一标识token
        String token = UUID.randomUUID().toString().replace("-","");

        //8.登录成功信息放入redis
        //key : token|value: 用户信息
        redisTemplate.opsForValue()
                .set("user:login"+token,JSON.toJSONString(sysUser),
                        7, TimeUnit.DAYS); //设置7天过期，当日有效

        //9.返回loginVo对象
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);

        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto,
                                        Integer pageNum,
                                        Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserList);
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        //1.判断用户名不能重复
        String userNameame=sysUser.getUserName();
        SysUser dbSysUser=sysUserMapper.selectUserInfoByUserName(userNameame);
        if(dbSysUser!=null){
            throw new SpzxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //2.输入的密码进行加密
        String inputPwd=sysUser.getPassword();
        //MD5加密
        String md5Pwd= DigestUtils.md5DigestAsHex(inputPwd.getBytes());
        sysUser.setPassword(md5Pwd);

        //3.设置status 1可用 0不可用
        sysUser.setStatus(1);

        sysUserMapper.saveSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        // 1.更新用户信息
        sysUserMapper.updateSysUser(sysUser);
        // 2.获取用户名
        String userName=sysUser.getUserName();
        // 3.检查更新后的用户名是否重复
        int count = sysUserMapper.selectCountByUserName(userName);
        if (count > 1) {
            // 4.如果用户名重复，抛出异常以触发事务回滚
            throw new SpzxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

    }

    @Override
    public void deleteSysUserById(Long userId) {
        sysUserMapper.deleteSysUserById(userId);
    }

    //用户分配角色
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //1.先查询之前是否分配过角色的数据，如果有就先删除
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());
        //2.重新分配数据、
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        //遍历得到每个角色id
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }
    }
}
