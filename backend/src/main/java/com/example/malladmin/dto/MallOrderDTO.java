package com.example.malladmin.dto;

import com.example.malladmin.entity.MallOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商城订单DTO，用于前后端交互
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallOrderDTO {

    private Long id;
    private String orderNo;
    private String customerName;
    private String receiverPhone;
    private String address;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime createdAt;

    /** 订单明细列表 */
    private List<OrderItemDTO> items;

    /**
     * 从实体转换为DTO
     *
     * @param order 订单实体
     * @return MallOrderDTO
     */
    public static MallOrderDTO fromEntity(MallOrder order) {
        MallOrderDTO dto = new MallOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNo(order.getOrderNo());
        dto.setCustomerName(order.getCustomerName());
        dto.setReceiverPhone(order.getReceiverPhone());
        dto.setAddress(order.getAddress());
        dto.setAmount(order.getAmount());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream()
                    .map(OrderItemDTO::fromEntity)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
