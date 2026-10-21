package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，统一捕获并格式化异常响应
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务参数异常
     *
     * @param exception 业务异常
     * @return 统一错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> handleIllegalArgument(IllegalArgumentException exception) {
        log.warn("业务异常: {}", exception.getMessage());
        return ApiResponse.fail(exception.getMessage());
    }

    /**
     * 处理所有其他未捕获异常
     *
     * @param exception 通用异常
     * @return 统一错误响应
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handle(Exception exception) {
        log.error("系统异常: {}", exception.getMessage(), exception);
        return ApiResponse.fail(exception.getMessage());
    }
}
