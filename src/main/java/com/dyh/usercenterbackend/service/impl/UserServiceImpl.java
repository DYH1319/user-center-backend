package com.dyh.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dyh.usercenterbackend.common.StatusCode;
import com.dyh.usercenterbackend.constant.UserConstant;
import com.dyh.usercenterbackend.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyh.usercenterbackend.mapper.UserMapper;
import com.dyh.usercenterbackend.model.domain.User;
import com.dyh.usercenterbackend.service.UserService;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @className UserServiceImpl
 * @version 1.0
 * @author DYH
 * @since 2023/9/2 0:36
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        // 校验非null非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) throw new BusinessException(StatusCode.PARAMS_ERROR, "参数为空");
        // 校验账户为4~8位
        if (userAccount.length() < 4 || userAccount.length() > 8) throw new BusinessException(StatusCode.PARAMS_ERROR, "账户长度不符合4~8位");
        // 校验密码为6~16位
        if (userPassword.length() < 6 || userPassword.length() > 16) throw new BusinessException(StatusCode.PARAMS_ERROR, "密码长度不符合6~16位");
        // 校验账户只能包含大小写字母、阿拉伯数字、下划线以及短横线
        if (!Pattern.compile("^[A-Za-z0-9_-]+$").matcher(userAccount).find()) throw new BusinessException(StatusCode.PARAMS_ERROR, "账户只能包含大小写字母、阿拉伯数字、下划线以及短横线");
        // 校验密码只能包含大小写字母、阿拉伯数字以及常见特殊符号
        if (!Pattern.compile("^[A-Za-z0-9!@#$%^&*<>?_-]+$").matcher(userPassword).find()) throw new BusinessException(StatusCode.PARAMS_ERROR, "密码只能包含大小写字母、阿拉伯数字以及!@#$%^&*<>?_-");
        // 校验密码需包含大写字母、小写字母、阿拉伯数字、以及常见特殊符号中的至少两项
        int containSum = 0;
        containSum = Pattern.compile("[A-Z]").matcher(userPassword).find() ? containSum + 1 : containSum;
        containSum = Pattern.compile("[a-z]").matcher(userPassword).find() ? containSum + 1 : containSum;
        containSum = Pattern.compile("[0-9]").matcher(userPassword).find() ? containSum + 1 : containSum;
        containSum = Pattern.compile("[!@#$%^&*<>?_-]").matcher(userPassword).find() ? containSum + 1 : containSum;
        if (containSum < 2) throw new BusinessException(StatusCode.PARAMS_ERROR, "密码需包含大写字母、小写字母、阿拉伯数字、特殊符号中的至少两项");
        // 校验校验密码和密码相同
        if (!userPassword.equals(checkPassword)) throw new BusinessException(StatusCode.PARAMS_ERROR, "两次输入的密码不相同");
        // 校验账户不能与已有账户重复（需要访问数据库的校验应该在校验的最后执行以优化性能）
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount);
        User selectUser = this.getOne(lambdaQueryWrapper);
        if (selectUser != null) throw new BusinessException(StatusCode.PARAMS_ERROR, "账户名已存在");
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) throw new BusinessException(StatusCode.SYSTEM_ERROR, "无法新建用户");
        return user.getId();
    }
    
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        // 校验非null非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) throw new BusinessException(StatusCode.PARAMS_ERROR, "参数为空");
        // 校验账户为4~8位
        if (userAccount.length() < 4 || userAccount.length() > 8) throw new BusinessException(StatusCode.PARAMS_ERROR, "账户长度不符合4~8位");
        // 校验密码为6~16位
        if (userPassword.length() < 6 || userPassword.length() > 16) throw new BusinessException(StatusCode.PARAMS_ERROR, "密码长度不符合6~16位");
        // 校验账户只能包含大小写字母、阿拉伯数字、下划线以及短横线
        if (!Pattern.compile("^[A-Za-z0-9_-]+$").matcher(userAccount).find()) throw new BusinessException(StatusCode.PARAMS_ERROR, "账户只能包含大小写字母、阿拉伯数字、下划线以及短横线");
        // 校验密码只能包含大小写字母、阿拉伯数字以及常见特殊符号
        if (!Pattern.compile("^[A-Za-z0-9!@#$%^&*<>?_-]+$").matcher(userPassword).find()) throw new BusinessException(StatusCode.PARAMS_ERROR, "密码只能包含大小写字母、阿拉伯数字以及!@#$%^&*<>?_-");
        // 校验密码需包含大写字母、小写字母、阿拉伯数字、以及常见特殊符号中的至少两项
        int containSum = 0;
        containSum = Pattern.compile("[A-Z]").matcher(userPassword).find() ? containSum + 1 : containSum;
        containSum = Pattern.compile("[a-z]").matcher(userPassword).find() ? containSum + 1 : containSum;
        containSum = Pattern.compile("[0-9]").matcher(userPassword).find() ? containSum + 1 : containSum;
        containSum = Pattern.compile("[!@#$%^&*<>?_-]").matcher(userPassword).find() ? containSum + 1 : containSum;
        if (containSum < 2) throw new BusinessException(StatusCode.PARAMS_ERROR, "密码需包含大写字母、小写字母、阿拉伯数字、特殊符号中的至少两项");
        // 2. 加密与校验账户密码是否正确
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        // 查询用户是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount);
        User user = this.getOne(lambdaQueryWrapper);
        if (user == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "账号不存在");
        }
        // 查询账户密码是否匹配
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount);
        lambdaQueryWrapper.eq(User::getUserPassword, encryptPassword);
        user = this.getOne(lambdaQueryWrapper);
        if (user == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "账号与密码不匹配");
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }
    
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 0;
    }
    
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) throw new BusinessException(StatusCode.PARAMS_ERROR);
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }
    
    @Override
    public List<User> searchUsersByUsername(String username, HttpServletRequest request) {
        if (!isAdmin(request)) return new ArrayList<>();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            lambdaQueryWrapper.like(User::getUsername, username);
        }
        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }
    
    @Override
    public boolean deleteUserById(long id, HttpServletRequest request) {
        if (id <= 0) throw new BusinessException(StatusCode.PARAMS_ERROR, "被删除的id小于等于0");
        if (!isAdmin(request)) throw new BusinessException(StatusCode.NO_AUTH, "您没有删除用户的权限");
        return this.removeById(id);
    }
    
    /**
     * 判断是否为管理员
     * @param request HttpServletRequest
     * @return 是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }
}
