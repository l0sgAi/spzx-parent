package com.losgai.spzx.manager.controller;

import com.losgai.spzx.manager.service.SysMenuService;
import com.losgai.spzx.manager.service.SysUserService;
import com.losgai.spzx.manager.service.ValidateCodeService;
import com.losgai.spzx.model.dto.system.LoginDto;
import com.losgai.spzx.model.entity.system.SysUser;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.system.LoginVo;
import com.losgai.spzx.model.vo.system.SysMenuVo;
import com.losgai.spzx.model.vo.system.ValidateCodeVo;
import com.losgai.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/menus")
    public Result menus(){ //查询用户可操作的菜单
        List<SysMenuVo> sysMenuVoList = sysMenuService.findMenuByUserId();
        return Result.build(sysMenuVoList,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo() { //从ThreadLocal里面直接获取信息
        return Result.build(AuthContextUtil.getUser(),ResultCodeEnum.SUCCESS);
    }

    //获取当前用户信息
    /*@GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader(name="token") String token) {
        //1.从请求头中获取token
        //2.根据token 查询Redis
        SysUser sysUser=sysUserService.getUserInfo(token);

        //3.返回用户信息
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }*/

    @GetMapping("/generateValidateCode")
    public Result generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    // 用户登录
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo=sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    //用户退出
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name="token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
