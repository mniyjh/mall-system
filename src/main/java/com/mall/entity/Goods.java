package com.mall.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体（对应商品表 goods）
 */
@Data
public class Goods {
    private Long goodsId;
    private String goodsName;
    private Long categoryId;
    private BigDecimal price;
    private Integer stock;
    private String image;
    private String description;
    /** 状态：1-上架，0-下架 */
    private Integer status;
    private LocalDateTime createTime;
}
