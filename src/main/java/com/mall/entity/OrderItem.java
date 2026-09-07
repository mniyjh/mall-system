package com.mall.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细实体（对应订单明细表 order_item）
 */
@Data
public class OrderItem {
    private Long itemId;
    private Long orderId;
    private Long goodsId;
    private String goodsName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
}
