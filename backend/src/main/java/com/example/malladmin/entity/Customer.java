package com.example.malladmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商城会员实体，映射customer表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 登录账号，唯一 */
    @Column(name = "username", unique = true, length = 50)
    private String username;

    /** 登录密码 */
    @Column(name = "password", length = 100)
    private String password;

    /** 会员昵称 */
    @Column(name = "nickname", length = 50)
    private String nickname;

    /** 手机号 */
    @Column(name = "phone", length = 20)
    private String phone;

    /** 会员等级名称 */
    @Column(name = "level_name", length = 20)
    private String levelName;

    /** 积分 */
    @Column(name = "points")
    private Integer points;

    /** 累计消费金额 */
    @Column(name = "total_spend", precision = 12, scale = 2)
    private BigDecimal totalSpend;

    /** 状态：正常/冻结 */
    @Column(name = "status", length = 10)
    private String status;

    /** 注册时间 */
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;
}
