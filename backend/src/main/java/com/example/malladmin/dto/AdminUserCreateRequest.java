package com.example.malladmin.dto;

import com.example.malladmin.entity.AdminUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 管理员用户创建/更新请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserCreateRequest {

    /** 用户ID（更新时必填） */
    private Long id;

    /** 登录账号 */
    @NotBlank(message = "账号不能为空")
    private String username;

    /** 登录密码 */
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 角色名称 */
    private String roleName;

    /** 手机号 */
    private String phone;

    /** 状态：启用/停用 */
    private String status;

    /**
     * 将请求转换为实体对象
     *
     * @return AdminUser实体
     */
    public AdminUser toEntity() {
        AdminUser entity = new AdminUser();
        entity.setId(this.id);
        entity.setUsername(this.username);
        entity.setPassword(this.password);
        entity.setRealName(this.realName);
        entity.setRoleName(this.roleName);
        entity.setPhone(this.phone);
        entity.setStatus(this.status);
        return entity;
    }
}
