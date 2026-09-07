package com.mall.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 购物车实体（对应购物车表 cart）
 */
@Data
public class Cart {
    private Long cartId;
    private Long userId;
    private Long goodsId;
    private Integer quantity;
    private LocalDateTime createTime;
}
