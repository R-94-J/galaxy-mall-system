package com.example.malladmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体，映射product表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 商品SKU编码 */
    @Column(name = "sku", length = 50)
    private String sku;

    /** 商品名称 */
    @Column(name = "name", length = 200)
    private String name;

    /** 所属分类名称 */
    @Column(name = "category_name", length = 50)
    private String categoryName;

    /** 售价 */
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    /** 成本价 */
    @Column(name = "cost_price", precision = 10, scale = 2)
    private BigDecimal costPrice;

    /** 库存数量 */
    @Column(name = "stock")
    private Integer stock;

    /** 销量 */
    @Column(name = "sales")
    private Integer sales;

    /** 状态：上架/下架 */
    @Column(name = "status", length = 10)
    private String status;

    /** 封面图URL */
    @Column(name = "cover_url", length = 500)
    private String coverUrl;

    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
