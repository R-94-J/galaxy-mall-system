package com.example.malladmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 商城订单实体，映射mall_order表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "items")
@Entity
@Table(name = "mall_order")
public class MallOrder {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 订单编号，唯一 */
    @Column(name = "order_no", unique = true, length = 50)
    private String orderNo;

    /** 客户姓名 */
    @Column(name = "customer_name", length = 50)
    private String customerName;

    /** 收货人手机号 */
    @Column(name = "receiver_phone", length = 20)
    private String receiverPhone;

    /** 收货地址 */
    @Column(name = "address", length = 200)
    private String address;

    /** 订单金额 */
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    /** 支付方式 */
    @Column(name = "payment_method", length = 20)
    private String paymentMethod;

    /** 订单状态：待付款/待发货/已发货/已完成/售后中 */
    @Column(name = "status", length = 20)
    private String status;

    /** 下单时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 订单明细列表 */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OrderItem> items = new ArrayList<>();
}
