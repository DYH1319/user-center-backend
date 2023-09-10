package com.dyh.usercenterbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyh.usercenterbackend.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DYH
 * @version 1.0
 * @className UserMapper
 * @since 2023/9/2 0:36
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}