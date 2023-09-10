package com.dyh.usercenterbackend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * @className User
 * @version 1.0
 * @author DYH
 * @since 2023/9/2 0:36
 */
@Data
@TableName(value = "`user`")
public class User {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 账号
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 用户头像
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 密码
     */
    // @TableField(value = "user_password", select = false)
    @TableField(value = "user_password")
    private String userPassword;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 状态：0 - 正常
     */
    @TableField(value = "user_status")
    private Integer userStatus;
    
    /**
     * 用户角色：0 - 普通用户 1 - 管理员
     */
    @TableField(value = "user_role")
    private Integer userRole;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否已逻辑删除
     */
    @TableLogic
    @TableField(value = "is_delete")
    private Byte isDelete;
}