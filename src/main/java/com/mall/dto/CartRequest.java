package com.mall.dto;

import lombok.Data;

/**
 * 购物车操作请求
 */
@Data
public class CartRequest {
    private Long userId;
    private Long goodsId;
    private Integer quantity;
}
