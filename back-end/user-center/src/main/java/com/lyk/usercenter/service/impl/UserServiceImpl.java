package com.lyk.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyk.usercenter.model.contant.UserContant;
import com.lyk.usercenter.model.domain.User;
import com.lyk.usercenter.service.UserService;
import com.lyk.usercenter.mapper.UserMapper;
import com.lyk.usercenter.utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
* @author alan
* @description 针对表【user(用户中心)】的数据库操作Service实现
* @createDate 2022-04-17 12:42:53
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    UserMapper userMapper;

    // 盐值



    @Override
    public long userRegister(String userName, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword)) {
            return -1;
        }
        if (userName.length() < 8) {
            return -1;
        }
        if (userPassword.length() < 8) {
            return -1;
        }
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        if (!AccountUtils.checkUserNameValid(userName)) {
            return -1;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }

        // 加密

        String encryptPassword = DigestUtils.md5DigestAsHex((UserContant.SALT + userPassword).getBytes());

        // 插入数据
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        log.info("save new user successfully");
        return user.getId();

    }

    @Override
    public User userLogin(String userName, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            return null;
        }
        if (userName.length() < 8) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        if (!AccountUtils.checkUserNameValid(userName)) {
            return null;
        }

        // 获取加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((UserContant.SALT + userPassword).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("the user is null");
            return null;
        }
        User safetyUser = getSafetyUser(user);

        // 记录登录态
        request.getSession().setAttribute(UserContant.USER_LOGIN_IN_STATUS, safetyUser);
        log.info("log in successfully！");
        return safetyUser;

    }

    @Override
    public User getSafetyUser(User originUser) {
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setUserPhone(originUser.getUserPhone());
        safetyUser.setUserEmail(originUser.getUserEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        return safetyUser;
    }



}




