package com.example.malladmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 登录响应DTO，包含认证token和用户基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    /** 认证令牌 */
    private String token;

    /** 用户信息 */
    private Map<String, Object> user;
}
