package com.dyh.usercenterbackend;

import com.dyh.usercenterbackend.mapper.UserMapper;
import com.dyh.usercenterbackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class UserCenterBackendApplicationTests {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserService userService;
    
    @Test
    void testMd5Encrypt() {
        final String SALT = "dyh";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "Password").getBytes(StandardCharsets.UTF_8));
        System.out.println(encryptPassword);
    }
    
    @Test
    void testUserRegister() {
        String userAccount = "";
        String userPassword = "abc123456";
        String checkPassword = "abc123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        userAccount = "dyh123";
        userPassword = null;
        checkPassword = null;
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        userAccount = "dyh";
        userPassword = "abc123456";
        checkPassword = "abc123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        userAccount = "dyh123";
        userPassword = "123";
        checkPassword = "123";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        userAccount = "dyh$123";
        userPassword = "abc123456";
        checkPassword = "abc123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        userAccount = "dyh123";
        userPassword = "abc,.123456";
        checkPassword = "abc,.123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        userAccount = "dyh123";
        userPassword = "abc$123456";
        checkPassword = "abc$123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertNotEquals(-1, result);
        
        userAccount = "dyh12345";
        userPassword = "123456";
        checkPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        userAccount = "dyh12345";
        userPassword = "abc123456";
        checkPassword = "abc12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        
        // 校验账户不能与已有账户重复
        userAccount = "dyh123";
        userPassword = "abc123456";
        checkPassword = "abc123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}
