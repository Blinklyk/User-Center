package com.lyk.usercenter.service;

import com.lyk.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author alan
* @description 针对表【user(用户中心)】的数据库操作Service
* @createDate 2022-04-17 12:42:53
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */

    long userRegister(String userName, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userName 登录用户名
     * @param userPassword 登录用户密码
     * @return
     */
    User userLogin(String userName, String userPassword, HttpServletRequest request);

    User getSafetyUser(User originUser);
}
