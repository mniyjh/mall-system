package com.mall.dto;

import lombok.Data;

import java.util.List;

/**
 * 下单请求
 */
@Data
public class OrderCreateRequest {
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Long goodsId;
        private Integer quantity;
    }
}
