package com.example.malladmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 订单明细实体，映射order_item表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "order")
@Entity
@Table(name = "order_item")
public class OrderItem {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 商品名称 */
    @Column(name = "product_name", length = 200)
    private String productName;

    /** 商品单价 */
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    /** 购买数量 */
    @Column(name = "quantity")
    private Integer quantity;

    /** 所属订单 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private MallOrder order;
}
