package com.example.malladmin.dto;

import com.example.malladmin.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 优惠券DTO，用于前后端交互
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

    private Long id;

    /** 优惠券名称 */
    @NotBlank(message = "优惠券名称不能为空")
    private String name;

    /** 使用门槛金额 */
    private BigDecimal thresholdAmount;

    /** 优惠金额 */
    private BigDecimal discountAmount;

    /** 已发放数量 */
    private Integer issuedCount;

    /** 已核销数量 */
    private Integer usedCount;

    /** 状态：未开始/进行中/已结束 */
    private String status;

    /** 生效开始日期 */
    private LocalDate startDate;

    /** 生效结束日期 */
    private LocalDate endDate;

    /**
     * 从实体转换为DTO
     *
     * @param coupon 优惠券实体
     * @return CouponDTO
     */
    public static CouponDTO fromEntity(Coupon coupon) {
        CouponDTO dto = new CouponDTO();
        dto.setId(coupon.getId());
        dto.setName(coupon.getName());
        dto.setThresholdAmount(coupon.getThresholdAmount());
        dto.setDiscountAmount(coupon.getDiscountAmount());
        dto.setIssuedCount(coupon.getIssuedCount());
        dto.setUsedCount(coupon.getUsedCount());
        dto.setStatus(coupon.getStatus());
        dto.setStartDate(coupon.getStartDate());
        dto.setEndDate(coupon.getEndDate());
        return dto;
    }

    /**
     * 将DTO转换为实体对象
     *
     * @return Coupon实体
     */
    public Coupon toEntity() {
        Coupon entity = new Coupon();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setThresholdAmount(this.thresholdAmount);
        entity.setDiscountAmount(this.discountAmount);
        entity.setIssuedCount(this.issuedCount);
        entity.setUsedCount(this.usedCount);
        entity.setStatus(this.status);
        entity.setStartDate(this.startDate);
        entity.setEndDate(this.endDate);
        return entity;
    }
}
