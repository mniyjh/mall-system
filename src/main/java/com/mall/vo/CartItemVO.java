package com.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车项视图对象（含商品信息）
 */
@Data
public class CartItemVO {
    private Long cartId;
    private Long goodsId;
    private String goodsName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
}
