package com.example.malladmin.dto;

import com.example.malladmin.entity.InventoryLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 库存流水DTO，用于前后端交互
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLogDTO {

    private Long id;

    /** 商品名称 */
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    /** 流水类型：入库/出库/预警 */
    private String type;

    /** 变动数量 */
    private Integer quantity;

    /** 变动后余额 */
    private Integer balance;

    /** 操作人 */
    private String operatorName;

    /** 备注 */
    private String remark;

    /** 操作时间 */
    private LocalDateTime createdAt;

    /**
     * 从实体转换为DTO
     *
     * @param log 库存流水实体
     * @return InventoryLogDTO
     */
    public static InventoryLogDTO fromEntity(InventoryLog log) {
        InventoryLogDTO dto = new InventoryLogDTO();
        dto.setId(log.getId());
        dto.setProductName(log.getProductName());
        dto.setType(log.getType());
        dto.setQuantity(log.getQuantity());
        dto.setBalance(log.getBalance());
        dto.setOperatorName(log.getOperatorName());
        dto.setRemark(log.getRemark());
        dto.setCreatedAt(log.getCreatedAt());
        return dto;
    }

    /**
     * 将DTO转换为实体对象
     *
     * @return InventoryLog实体
     */
    public InventoryLog toEntity() {
        InventoryLog entity = new InventoryLog();
        entity.setId(this.id);
        entity.setProductName(this.productName);
        entity.setType(this.type);
        entity.setQuantity(this.quantity);
        entity.setBalance(this.balance);
        entity.setOperatorName(this.operatorName);
        entity.setRemark(this.remark);
        entity.setCreatedAt(this.createdAt);
        return entity;
    }
}
