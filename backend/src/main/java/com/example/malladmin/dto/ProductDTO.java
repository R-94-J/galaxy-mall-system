package com.example.malladmin.dto;

import com.example.malladmin.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 商品DTO，用于前后端交互
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    /** 商品SKU编码 */
    private String sku;

    /** 商品名称 */
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /** 所属分类名称 */
    private String categoryName;

    /** 售价 */
    private BigDecimal price;

    /** 成本价 */
    private BigDecimal costPrice;

    /** 库存数量 */
    private Integer stock;

    /** 销量 */
    private Integer sales;

    /** 状态：上架/下架 */
    private String status;

    /** 封面图URL */
    private String coverUrl;

    /**
     * 从实体转换为DTO
     *
     * @param product 商品实体
     * @return ProductDTO
     */
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setCategoryName(product.getCategoryName());
        dto.setPrice(product.getPrice());
        dto.setCostPrice(product.getCostPrice());
        dto.setStock(product.getStock());
        dto.setSales(product.getSales());
        dto.setStatus(product.getStatus());
        dto.setCoverUrl(product.getCoverUrl());
        return dto;
    }

    /**
     * 将DTO转换为实体对象
     *
     * @return Product实体
     */
    public Product toEntity() {
        Product entity = new Product();
        entity.setId(this.id);
        entity.setSku(this.sku);
        entity.setName(this.name);
        entity.setCategoryName(this.categoryName);
        entity.setPrice(this.price);
        entity.setCostPrice(this.costPrice);
        entity.setStock(this.stock);
        entity.setSales(this.sales);
        entity.setStatus(this.status);
        entity.setCoverUrl(this.coverUrl);
        return entity;
    }
}
