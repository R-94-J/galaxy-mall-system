package com.example.malladmin.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 商城会员注册请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopRegisterRequest {

    /** 登录账号 */
    @NotBlank(message = "账号不能为空")
    private String username;

    /** 登录密码 */
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 会员昵称 */
    private String nickname;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
