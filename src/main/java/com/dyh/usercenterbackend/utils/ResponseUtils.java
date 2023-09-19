package com.dyh.usercenterbackend.utils;

import com.dyh.usercenterbackend.common.BaseResponse;
import com.dyh.usercenterbackend.common.StatusCode;

/**
 * @author DYH
 * @version 1.0
 * @className ResponseUtils
 * @since 2023/9/14 10:02
 */
public class ResponseUtils {
    
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(StatusCode.SUCCESS, data);
    }
    
    public static <T> BaseResponse<T> success(T data, String description) {
        return new BaseResponse<>(StatusCode.SUCCESS, data, description);
    }
    
    public static <T> BaseResponse<T> fail(StatusCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
    
    public static <T> BaseResponse<T> fail(StatusCode errorCode, String description) {
        return new BaseResponse<>(errorCode, description);
    }
}
