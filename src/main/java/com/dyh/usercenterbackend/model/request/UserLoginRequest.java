package com.dyh.usercenterbackend.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 * @author DYH
 * @version 1.0
 * @className UserRegisterRequest
 * @since 2023/9/8 10:02
 */
@Data
public class UserLoginRequest implements Serializable {
    
    private static final long serialVersionUID = 475658407719779579L;
    
    private String userAccount;
    private String userPassword;
}
