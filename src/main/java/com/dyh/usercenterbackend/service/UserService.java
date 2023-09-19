package com.dyh.usercenterbackend.service;

import com.dyh.usercenterbackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @className UserService
 * @version 1.0
 * @author DYH
 * @since 2023/9/2 0:36
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
    
    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
    
    /**
     * 用户注销
     * @param request HttpServletRequest
     */
    int userLogout(HttpServletRequest request);
    
    /**
     * 用户脱敏
     * @param originUser 原始用户信息
     * @return 脱敏后的用户信息
     */
    User getSafetyUser(User originUser);
    
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @param request HttpServletRequest
     * @return 与用户名模糊匹配的所有用户
     */
    List<User> searchUsersByUsername(String username, HttpServletRequest request);
    
    /**
     * 根据id逻辑删除用户
     * @param id id
     * @param request HttpServletRequest
     * @return 是否删除成功
     */
    boolean deleteUserById(long id, HttpServletRequest request);
}
