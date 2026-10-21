package com.example.malladmin.dto;

import com.example.malladmin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 商品分类DTO，用于前后端交互
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    /** 分类名称 */
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /** 父级分类ID */
    private Long parentId;

    /** 排序序号 */
    private Integer sortOrder;

    /** 图标名称 */
    private String icon;

    /** 状态：启用/停用 */
    private String status;

    /**
     * 从实体转换为DTO
     *
     * @param category 分类实体
     * @return CategoryDTO
     */
    public static CategoryDTO fromEntity(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentId(category.getParentId());
        dto.setSortOrder(category.getSortOrder());
        dto.setIcon(category.getIcon());
        dto.setStatus(category.getStatus());
        return dto;
    }

    /**
     * 将DTO转换为实体对象
     *
     * @return Category实体
     */
    public Category toEntity() {
        Category entity = new Category();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setParentId(this.parentId);
        entity.setSortOrder(this.sortOrder);
        entity.setIcon(this.icon);
        entity.setStatus(this.status);
        return entity;
    }
}
