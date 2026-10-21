package com.example.malladmin.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商城下单请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderRequest {

    /** 会员ID */
    private Long customerId;

    /** 客户姓名 */
    private String customerName;

    /** 收货人手机号 */
    private String receiverPhone;

    /** 收货地址 */
    private String address;

    /** 支付方式 */
    private String paymentMethod;

    /** 订单金额 */
    private BigDecimal amount;

    /** 订单商品列表 */
    private List<Item> items;

    /**
     * 订单商品明细
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {

        /** 商品名称 */
        private String productName;

        /** 商品单价 */
        private BigDecimal price;

        /** 购买数量 */
        private Integer quantity;
    }
}
