package com.example.malladmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 优惠券实体，映射coupon表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 优惠券名称 */
    @Column(name = "name", length = 100)
    private String name;

    /** 使用门槛金额 */
    @Column(name = "threshold_amount", precision = 10, scale = 2)
    private BigDecimal thresholdAmount;

    /** 优惠金额 */
    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    /** 已发放数量 */
    @Column(name = "issued_count")
    private Integer issuedCount;

    /** 已核销数量 */
    @Column(name = "used_count")
    private Integer usedCount;

    /** 状态：未开始/进行中/已结束 */
    @Column(name = "status", length = 10)
    private String status;

    /** 生效开始日期 */
    @Column(name = "start_date")
    private LocalDate startDate;

    /** 生效结束日期 */
    @Column(name = "end_date")
    private LocalDate endDate;
}
