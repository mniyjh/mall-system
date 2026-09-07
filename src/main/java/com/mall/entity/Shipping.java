package com.mall.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 物流实体（对应物流表 shipping）
 */
@Data
public class Shipping {
    private Long shippingId;
    private Long orderId;
    private String trackingNo;
    private String company;
    private LocalDateTime shipTime;
}
