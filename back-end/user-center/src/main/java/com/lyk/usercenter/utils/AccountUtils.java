package com.lyk.usercenter.utils;

import com.lyk.usercenter.model.contant.UserContant;
import com.lyk.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

public class AccountUtils {

    public static boolean checkUserNameValid(String userName) {
        int n = userName.length();
        boolean flag = true;
        for (int i = 0; i < n; i++) {
            char c = userName.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    // 鉴权工具类
    public static boolean isAdmin(HttpServletRequest request) {
        Object userObj = (User) request.getSession().getAttribute(UserContant.USER_LOGIN_IN_STATUS);
        User user = (User) userObj; // 不这样写还调用不了User的实例方法？
        if (user == null || user.getUserRole() != UserContant.ADMIN_ROLE) {
            return false;
        }
        return true;
    }
}
