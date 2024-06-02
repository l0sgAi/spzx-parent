package com.losgai.spzx.utils;

import com.losgai.spzx.model.entity.system.SysUser;
import com.losgai.spzx.model.entity.user.UserInfo;

public class AuthContextUtil {
    //创建ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    //添加数据
    public static void setUser(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    //获取数据
    public static SysUser getUser() {
        return threadLocal.get();
    }

    //删除数据
    public static void removeUser() {
        threadLocal.remove();
    }

    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }



}
