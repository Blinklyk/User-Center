package com.lyk.usercenter.service;
import java.util.Date;

import com.lyk.usercenter.mapper.UserMapper;
import com.lyk.usercenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    UserService userService;

    @Test
    void testAssertAddUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("lyk");
        user.setAvatarUrl("http:...");
        user.setGender(1);
        user.setUserPassword("lykpassword");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void testUserRegister() {
        String userName = "lykuserName";
        String userPassword = "";
        String checkPassword = "lyk123456";
        long result = userService.userRegister(userName, userPassword, userPassword);
        Assert.assertEquals(-1, result);

        userName = "lykuserName";
        userPassword = "lyk123456";
        checkPassword = "lyk123";
        result = userService.userRegister(userName, userPassword, userPassword);
        Assert.assertEquals(-1, result);

        userName = "lykuserName!";
        userPassword = "lyk123456";
        checkPassword = "lyk123456";
        result = userService.userRegister(userName, userPassword, userPassword);
        Assertions.assertEquals(-1, result);

        userName = "lykuserName";
        userPassword = "lyk123456";
        checkPassword = "lyk123456";
        result = userService.userRegister(userName, userPassword, userPassword);
        assertEquals(-1, result);

    }

    @Test
    void addAnUser() {
//        String userName = "lykUserName1";
//        String userPassword = "12345678";
//        String checkPassword = "12345678";
//        long result = userService.userRegister(userName, userPassword, userPassword);
//        Assert.assertNotEquals(-1, result);
        String userName = "lykAdmin";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        long result = userService.userRegister(userName, userPassword, userPassword);
        Assert.assertNotEquals(-1, result);
    }

}