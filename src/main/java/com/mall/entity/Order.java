package com.mall.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体（对应订单表 orders）
 */
@Data
public class Order {
    /** 订单状态：待支付 */
    public static final int STATUS_UNPAID = 0;
    /** 订单状态：待发货 */
    public static final int STATUS_TO_SHIP = 1;
    /** 订单状态：已发货 */
    public static final int STATUS_SHIPPED = 2;
    /** 订单状态：已完成 */
    public static final int STATUS_COMPLETED = 3;
    /** 订单状态：已取消 */
    public static final int STATUS_CANCELLED = 4;

    private Long orderId;
    private String orderNo;
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private BigDecimal totalAmount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    private LocalDateTime shipTime;
}
