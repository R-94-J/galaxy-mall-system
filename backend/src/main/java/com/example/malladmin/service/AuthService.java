package com.example.malladmin.service;

import com.example.malladmin.dto.LoginRequest;
import com.example.malladmin.dto.LoginResponse;

/**
 * 管理员认证服务接口
 */
public interface AuthService {

    /**
     * 管理员登录认证
     *
     * @param request 登录请求
     * @return 登录响应（含token和用户信息）
     */
    LoginResponse login(LoginRequest request);
}
