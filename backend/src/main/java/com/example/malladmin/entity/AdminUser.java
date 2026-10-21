package com.example.malladmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 后台管理员用户实体，映射admin_user表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_user")
public class AdminUser {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 登录账号，唯一 */
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    /** 登录密码 */
    @Column(name = "password", length = 100)
    private String password;

    /** 真实姓名 */
    @Column(name = "real_name", length = 50)
    private String realName;

    /** 角色名称 */
    @Column(name = "role_name", length = 50)
    private String roleName;

    /** 手机号 */
    @Column(name = "phone", length = 20)
    private String phone;

    /** 状态：启用/停用 */
    @Column(name = "status", length = 10)
    private String status;

    /** 最近登录时间 */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
