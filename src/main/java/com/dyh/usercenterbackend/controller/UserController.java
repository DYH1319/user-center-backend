package com.dyh.usercenterbackend.controller;

import com.dyh.usercenterbackend.model.domain.User;
import com.dyh.usercenterbackend.model.request.UserLoginRequest;
import com.dyh.usercenterbackend.model.request.UserRegisterRequest;
import com.dyh.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 * // @RestController适用于编写restful风格的api，返回值默认为json类型
 * @author DYH
 * @version 1.0
 * @className UserController
 * @since 2023/9/8 9:49
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) return null;
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) return null;
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }
    
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) return null;
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) return null;
        return userService.userLogin(userAccount, userPassword, request);
    }
    
    @GetMapping("/search")
    public List<User> searchUsersByUsername(String username, HttpServletRequest request) {
        return userService.searchUsersByUsername(username, request);
    }
    
    // @DeleteMapping("/")
    @PostMapping("/delete")
    public boolean deleteUserById(@RequestBody long id, HttpServletRequest request) {
        if (id <= 0) return false;
        return userService.deleteUserById(id, request);
    }
}
