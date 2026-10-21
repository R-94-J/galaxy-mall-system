package com.example.malladmin.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 商城会员登录请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopLoginRequest {

    /** 账号或手机号 */
    @NotBlank(message = "账号不能为空")
    private String account;

    /** 登录密码 */
    @NotBlank(message = "密码不能为空")
    private String password;
}
