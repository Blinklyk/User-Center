package com.lyk.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyk.usercenter.model.contant.UserContant;
import com.lyk.usercenter.model.domain.User;
import com.lyk.usercenter.model.request.UserLoginRequest;
import com.lyk.usercenter.model.request.UserRegisterRequest;
import com.lyk.usercenter.service.UserService;
import com.lyk.usercenter.utils.AccountUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userName, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User UserLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            return null;
        }
        return userService.userLogin(userName, userPassword, request);
    }

    @PostMapping("/search")
    public List<User> searchUser(String userName, HttpServletRequest request) {
        // 鉴权
        if (!AccountUtils.isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userName", userName);
        return userService.list().stream().map(user -> {
            user.setUserPassword(null);
            return user;
        }).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(int delId, HttpServletRequest request) {
        if (!AccountUtils.isAdmin(request)) {
            return false;
        }
        if (delId < 0) {
            return false;
        }
        return userService.removeById(delId);
    }
}
