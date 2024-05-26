package com.losgai.spzx.utils;

import com.losgai.spzx.model.entity.system.SysUser;

public class AuthContextUtil {
    //创建ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

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

}
