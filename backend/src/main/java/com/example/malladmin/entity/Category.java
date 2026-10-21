package com.example.malladmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 商品分类实体，映射category表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 分类名称 */
    @Column(name = "name", length = 50)
    private String name;

    /** 父级分类ID，0表示顶级 */
    @Column(name = "parent_id")
    private Long parentId;

    /** 排序序号 */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /** 图标名称 */
    @Column(name = "icon", length = 50)
    private String icon;

    /** 状态：启用/停用 */
    @Column(name = "status", length = 10)
    private String status;
}
