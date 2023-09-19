package com.dyh.usercenterbackend.controller;

import com.dyh.usercenterbackend.common.BaseResponse;
import com.dyh.usercenterbackend.common.StatusCode;
import com.dyh.usercenterbackend.constant.UserConstant;
import com.dyh.usercenterbackend.exception.BusinessException;
import com.dyh.usercenterbackend.model.domain.User;
import com.dyh.usercenterbackend.model.request.UserLoginRequest;
import com.dyh.usercenterbackend.model.request.UserRegisterRequest;
import com.dyh.usercenterbackend.service.UserService;
import com.dyh.usercenterbackend.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 * // @RestController适用于编写restful风格的api，返回值默认为json类型
 *
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "参数为空");
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResponseUtils.success(result, "注册成功!");
    }
    
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "参数为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResponseUtils.success(user, "登录成功!");
    }
    
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        if (result != 0) {
            throw new BusinessException(StatusCode.UNKNOWN_ERROR, "注销失败");
        }
        return ResponseUtils.success(result, "注销成功!");
    }
    
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (currentUser == null) {
            throw new BusinessException(StatusCode.NOT_LOGIN, "未登录");
        }
        long userId = currentUser.getId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResponseUtils.success(safetyUser);
    }
    
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsersByUsername(String username, HttpServletRequest request) {
        List<User> userList = userService.searchUsersByUsername(username, request);
        return ResponseUtils.success(userList);
    }
    
    // @DeleteMapping("/")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserById(@RequestBody long id, HttpServletRequest request) {
        boolean result = userService.deleteUserById(id, request);
        return ResponseUtils.success(result, "删除用户成功!");
    }
}
