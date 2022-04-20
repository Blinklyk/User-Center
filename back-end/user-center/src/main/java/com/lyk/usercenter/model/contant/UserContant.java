package com.lyk.usercenter.model.contant;

public interface UserContant {

    /**
     * 盐值
     */
    public static final String SALT = "SALT";

    /**
     * 用户角色 role
     * 0 - 普通用户
     * 1- 管理员
     */
    public static final int USER_ROLE = 0;
    public static final int ADMIN_ROLE = 1;

    /**
     * 登录状态session中
     * 0 - 已登录
     */
    public final static String USER_LOGIN_IN_STATUS = "0";

}
