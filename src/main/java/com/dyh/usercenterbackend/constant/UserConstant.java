package com.dyh.usercenterbackend.constant;

/**
 * 用户常量
 * @author DYH
 * @version 1.0
 * @className UserConstant
 * @since 2023/9/8 11:09
 */
public interface UserConstant {
    
    /**
     * 用户密码加密盐值，混淆密码
     */
    String SALT = "dyh";
    
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";
    
    // -------- 权限 --------
    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;
    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;
}
