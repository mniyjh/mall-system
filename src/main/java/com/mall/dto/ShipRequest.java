package com.mall.dto;

import lombok.Data;

/**
 * 发货/物流操作请求
 */
@Data
public class ShipRequest {
    private Long orderId;
    private String trackingNo;
    private String company;
}
