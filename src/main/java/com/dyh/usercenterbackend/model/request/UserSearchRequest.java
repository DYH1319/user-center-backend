package com.dyh.usercenterbackend.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DYH
 * @version 1.0
 * @className UserSearchRequest
 * @since 2023/9/20 11:37
 */
@Data
public class UserSearchRequest implements Serializable {
    
    private static final long serialVersionUID = 3150702416297610620L;
    
    private String username;
    private String userAccount;
    private String avatarUrl;
    private Byte gender;
    private String phone;
    private String email;
    private Integer userStatus;
    private Integer userRole;
    private String startTime;
    private String endTime;
    // TODO: 后端分页
}
