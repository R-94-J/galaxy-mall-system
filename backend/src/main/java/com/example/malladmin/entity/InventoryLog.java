package com.example.malladmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 库存流水实体，映射inventory_log表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_log")
public class InventoryLog {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 商品名称 */
    @Column(name = "product_name", length = 200)
    private String productName;

    /** 流水类型：入库/出库/预警 */
    @Column(name = "type", length = 10)
    private String type;

    /** 变动数量 */
    @Column(name = "quantity")
    private Integer quantity;

    /** 变动后余额 */
    @Column(name = "balance")
    private Integer balance;

    /** 操作人 */
    @Column(name = "operator_name", length = 50)
    private String operatorName;

    /** 备注 */
    @Column(name = "remark", length = 200)
    private String remark;

    /** 操作时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
